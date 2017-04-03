package com.softserve.osbb.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;

/**
 * GoogleDrive-specific service.
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 24.12.2016
 */
@Service
public interface GoogleDriveService {
    
    File createFolder(String name, String parentId);
    
    String delete(String id);
    
    File getFile(String id);
    
    List<File> getAll();
    
    List<File> findByParentId(String id);
    
    File update(String id, String name);

    void upload(MultipartFile file, String folderId);

    void download(String id, HttpServletResponse response);
    
    void uploadUserPhoto(MultipartFile file, String userEmail);
    
    public InputStream getInput(String fileId) throws IOException;
    
    public void insertChatFile (String description, String fileName, java.io.File file);
    
    public File findByName(String folderName, String parentId);
    
    public void validateEmptyFile();
    
}
