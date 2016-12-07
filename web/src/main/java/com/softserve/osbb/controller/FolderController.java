package com.softserve.osbb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.FolderDTO;
import com.softserve.osbb.dto.mappers.FolderMapper;
import com.softserve.osbb.model.Folder;
import com.softserve.osbb.service.FolderService;

/**
 * Folder specific controller.
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
@CrossOrigin
@RestController
@RequestMapping("restful/folder")
public class FolderController {
    
    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);
    
    @Autowired
    private FolderService service; 
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<FolderDTO>> getAll() {
        
        System.out.println("\ngetAll()\n");
        List<Folder> folders = new ArrayList<>();
        folders.addAll(service.findAll());
        List<FolderDTO> response = new ArrayList<>();
        for (Folder folder: folders) {
            response.add(FolderMapper.toDTO(folder));
        }
        return new ResponseEntity<List<FolderDTO>>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{parentId}", method = RequestMethod.GET)
    public ResponseEntity<List<FolderDTO>> findByParent(@PathVariable Integer parentId) {
        
        System.out.println("\nfindByParent()\n");
        
        List<Folder> folders = new ArrayList<>();
        folders.addAll(service.findByParentId(parentId));
        List<FolderDTO> response = new ArrayList<>();
        for (Folder folder: folders) {
            response.add(FolderMapper.toDTO(folder));
        }
        return new ResponseEntity<List<FolderDTO>>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{parentId}", method = RequestMethod.POST)
    public ResponseEntity<FolderDTO> saveFolder(@RequestBody String folderName, @PathVariable Integer parentId) {
        
        Folder newFolder = service.save(folderName, parentId);
        System.out.println(newFolder);
        
        logger.info("New folder named '" + folderName + "' created.");
        FolderDTO folder = new FolderDTO(newFolder.getId(), newFolder.getName());
        return new ResponseEntity<FolderDTO>(folder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Folder> updateFolder() {
        return null;
    }
    
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Folder> deleteFolder() {
        return null;
    }

}
