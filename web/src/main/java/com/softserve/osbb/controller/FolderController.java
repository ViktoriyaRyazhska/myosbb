package com.softserve.osbb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.FolderDTO;
import com.softserve.osbb.dto.mappers.FolderMapper;
import com.softserve.osbb.model.Folder;
import com.softserve.osbb.service.FolderService;
import com.softserve.osbb.service.exceptions.EntityNotFoundException;
import com.softserve.osbb.util.resources.exceptions.Error;

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
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<FolderDTO> getAll() {
        List<Folder> folders = new ArrayList<>();
        folders.addAll(service.findAll());
        
        List<FolderDTO> response = new ArrayList<>();        
        for (Folder folder: folders) {
            response.add(FolderMapper.toDTO(folder));
        }
        
        return response;
    }    
    
    @RequestMapping(value = "parent/{parentId}", method = RequestMethod.GET)
    public ResponseEntity<List<FolderDTO>> findByParent(@PathVariable Integer parentId) {
        List<Folder> folders = new ArrayList<>();
        folders.addAll(service.findByParentId(parentId));
        
        List<FolderDTO> response = new ArrayList<>();        
        for (Folder folder: folders) {
            response.add(FolderMapper.toDTO(folder));
        }
        
        return new ResponseEntity<List<FolderDTO>>(response, HttpStatus.OK);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "parent/{parentId}", method = RequestMethod.POST)
    public FolderDTO saveFolder(@RequestBody String folderName, @PathVariable Integer parentId) {        
        Folder newFolder = service.save(folderName, parentId);        
        logger.info("New folder named '" + folderName + "' created. Id = " + newFolder.getId());
        return new FolderDTO(newFolder.getId(), newFolder.getName());
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Error folderNotFound(EntityNotFoundException exception) {
        String message = "Folder with id=" + exception.getId() + " not found!";
        logger.error(message);
        System.out.println(message);
        return new Error(404, message);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
    public FolderDTO getFolder(@PathVariable Integer id) {        
        Folder newFolder = service.findById(id);  
        System.out.println("Entity with id=" + id + " not found!");
        return new FolderDTO(newFolder.getId(), newFolder.getName());
    }

}
