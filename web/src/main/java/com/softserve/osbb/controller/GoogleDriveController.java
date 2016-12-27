package com.softserve.osbb.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.softserve.osbb.dto.DriveFile;
import com.softserve.osbb.service.GoogleDriveService;
import com.softserve.osbb.service.exceptions.GoogleDriveException;
import com.softserve.osbb.util.resources.exceptions.Error;

@CrossOrigin()
@RestController
@RequestMapping("/restful/google-drive")
public class GoogleDriveController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveController.class);
    
    private final String FOLDER_FLAG = "application/vnd.google-apps.folder";
    
    @Autowired
    private GoogleDriveService driveService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DriveFile getFile(@PathVariable String id) {
        return getDriveFileFrom(driveService.getFile(id));
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "root", method = RequestMethod.GET)
    public List<DriveFile> getRootFiles() {
        return getDriveFilesFrom(driveService.findByParentId("root"));
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "parent/{id}", method = RequestMethod.GET)
    public List<DriveFile> findByParentId(@PathVariable String id) {
        return getDriveFilesFrom(driveService.findByParentId(id));
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "create/{parentId}", method = RequestMethod.POST)
    public DriveFile createFolder(@PathVariable String parentId, @RequestBody String name) {        
        return getDriveFileFrom(driveService.create(name, parentId));        
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "delete", method = RequestMethod.PUT)
    public String delete(@RequestBody String id) {        
        return driveService.delete(id);        
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public DriveFile update(@PathVariable String id, @RequestBody String name) {        
        return getDriveFileFrom(driveService.update(id, name));
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "upload/{id}", method = RequestMethod.POST)    
    public void upload(@PathVariable String id, @RequestParam("file") MultipartFile file) {        
        driveService.upload(file, id);
    }    
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "download/{id}", method = RequestMethod.GET)    
    public void download(@PathVariable String id, HttpServletResponse response) {
        System.out.println("Controller got download request...");
        driveService.download(id, response);
        System.out.println("Controller is done!");
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public Error illegalFileName(IllegalArgumentException exception) {
        String message = exception.getLocalizedMessage();
        LOGGER.error(message);
        return new Error(404, message);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GoogleDriveException.class)
    public Error googleDriveError(GoogleDriveException exception) {
        String message = exception.getMessage();
        LOGGER.error(message);
        return new Error(500, message);
    }
    
    private List<DriveFile> getDriveFilesFrom(List<File> list) {        
        List<DriveFile> files = new LinkedList<>();
        list.forEach(file -> {
            files.add(getDriveFileFrom(file));            
        });
        return files;
    }
    
    private DriveFile getDriveFileFrom(File file) {
        DriveFile driveFile = new DriveFile();
        driveFile.setId(file.getId());
        driveFile.setName(file.getName());
        driveFile.setFolder(file.getMimeType().equals(FOLDER_FLAG));
        driveFile.setParents(file.getParents());
        return driveFile;
    }
    
}
