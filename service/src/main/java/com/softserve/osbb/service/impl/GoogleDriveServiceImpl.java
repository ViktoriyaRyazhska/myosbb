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

    /** Drive service. */
    private Drive driveService;

    /** Application name. Arbitrary. */
    private final String APPLICATION_NAME = "MyOSBB";

    /** Global instance of the JSON factory. */
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes. */
//    private final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE, DriveScopes.DRIVE_APPDATA);
    private final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_APPDATA);
    
    private final String APP_FOLDER = "appDataFolder";

    {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Credential credential = GoogleCredential
                    .fromStream(new FileInputStream("D:/workspace/GoogleDrive/src/yougetit/MyOSBB.json"))
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
        File fileMetadata = new File();
        fileMetadata.setName(name);
        fileMetadata.setParents(Collections.singletonList(parentId));
        fileMetadata.setMimeType(FOLDER_FLAG);

        File file = null;
        try {
            file = driveService.files().create(fileMetadata).setFields(CORE).execute();
            LOGGER.info(new StringBuilder("Folder ").append(file.getName())
                            .append(" with id=").append(file.getId())
                            .append(" was created").toString());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return file;
    }

    @Override
    public String delete(String id) {
        try {
            driveService.files().delete(id).execute();
            LOGGER.info("File with id=" + id + " was deleted");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
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
        }
        return result;
    }

    @Override
    public List<File> findByParentId(String id) {
        LOGGER.info("Getting content for folder with id=" + id);
        List<File> result = new ArrayList<>();
        try {
            List<File> files = driveService.files().list()
                    .setQ("'" + id + "' in parents").setSpaces(APP_FOLDER)
                    .setFields("nextPageToken, files(" + CORE + ")").execute()
                    .getFiles();
            result.addAll(files);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public File update(String id, String name) {
        File file = getFileWithFields(id, "name");
        file.setName(name);

        try {
            driveService.files().update(id, file).setFields("name").execute();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return getFileWithFields(id, CORE);
    }

}
