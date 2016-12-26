package com.softserve.osbb.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.softserve.osbb.service.GoogleDriveService;
import com.softserve.osbb.service.exceptions.GoogleDriveException;

/**
 * GoogleDrive-specific service implementation.
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 24.12.2016
 */
@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    private final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveServiceImpl.class);

    /** Core File fields we will work with. */
    private final String CORE = "id, name, mimeType, parents";

    /** Folder flag in File object. */
    private final String FOLDER_FLAG = "application/vnd.google-apps.folder";

    /** GoogleDrive service. */
    private Drive driveService;

    /** Application name. Arbitrary as we use service account. */
    private final String APPLICATION_NAME = "MyOSBB";

    /** Global instance of the JSON factory. */
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes. DRIVE_APPDATA provide r/w access only to appDataFolder. */
    private final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_APPDATA);
    
    /** Google's name for application root folder. Can be use in any place where fileId is needed. */
    private final String APP_FOLDER = "appDataFolder";

    {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); 
            java.io.File file = new java.io.File("src/main/resources/MyOSBB.json");
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getPath());
            Credential credential = GoogleCredential
                    .fromStream(new FileInputStream(file))                    
                    .createScoped(SCOPES);

            driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public File create(String name, String parentId) {
        validateName(name);
        checkIfExist(name, parentId);
        
        File file = new File();
        file.setName(name);
        file.setParents(Collections.singletonList(parentId));
        file.setMimeType(FOLDER_FLAG);

        File created = null;
        try {
            created = driveService.files().create(file).setFields(CORE).execute();
            LOGGER.info(new StringBuilder("Folder ").append(created.getName())
                            .append(" with id=").append(created.getId())
                            .append(" was created").toString());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new GoogleDriveException("Could not create folder " + name); 
        }

        return created;
    }

    private void validateName(String folderName) {
        if (!folderName.matches("[а-яА-ЯіІїЇa-zA-Z0-9-_.]{1,35}")) {
            throw new IllegalArgumentException("Folder name '" + folderName + "' not allowed!");
        }
    }
    
    private void checkIfExist(String folderName, String parentId) {
        findByParentId(parentId).forEach(file -> {
            if (file.getName().equalsIgnoreCase(folderName)) {
                throw new IllegalArgumentException("Folder '" + folderName + "' already exist!");
            }
        });
    }
    
    @Override
    public String delete(String id) {
        try {
            driveService.files().delete(id).execute();
            LOGGER.info("File with id=" + id + " was deleted");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Folder with id = '" + id + "' does not exist!");
        }
        return id;
    }

    @Override
    public File getFile(String id) {
        return getFileWithFields(id, CORE);
    }

    private File getFileWithFields(String id, String fields) {
        File file = null;
        try {
            file = driveService.files().get(id).setFields(fields).execute();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("File with id = '" + id + "' does not exist!");
        }

        return file;
    }

    @Override
    public List<File> getAll() {
        List<File> result = new ArrayList<>();
        try {
            List<File> files = driveService.files().list()
                    .setFields("nextPageToken, files(" + CORE + ")")
                    .execute().getFiles();
            result.addAll(files);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new GoogleDriveException("Could not retrieve files");
        }
        return result;
    }

    @Override
    public List<File> findByParentId(String id) {
        List<File> result = new ArrayList<>();
        try {
            List<File> files = driveService.files().list()
                    .setQ("'" + id + "' in parents").setSpaces(APP_FOLDER)
                    .setFields("nextPageToken, files(" + CORE + ")").execute()
                    .getFiles();
            result.addAll(files);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Folder with id = '" + id + "' does not exist!");
        }
        return result;
    }

    @Override
    public File update(String id, String name) {
        validateName(name);
        
        File file = getFileWithFields(id, "name");
        file.setName(name);

        try {
            driveService.files().update(id, file).setFields("name").execute();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new GoogleDriveException("Could not update " + name);
        }

        return getFileWithFields(id, CORE);
    }
    
}
