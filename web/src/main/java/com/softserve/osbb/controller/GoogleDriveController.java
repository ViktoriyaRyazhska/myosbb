package com.softserve.osbb.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.File;
import com.softserve.osbb.dto.DriveFile;
import com.softserve.osbb.service.GoogleDriveService;

@CrossOrigin
@RestController
@RequestMapping("/restful/google-drive")
public class GoogleDriveController {

    private static final String FOLDER_FLAG = "application/vnd.google-apps.folder";
    
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

//    private void m() {
//        logger.error("");
//    }
    
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
