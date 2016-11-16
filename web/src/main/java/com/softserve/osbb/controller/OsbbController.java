/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import com.softserve.osbb.dto.OsbbDTO;
import com.softserve.osbb.dto.mappers.OsbbDTOMapper;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.service.OsbbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Roman on 12.07.2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/osbb")
public class OsbbController {

    private static Logger logger = LoggerFactory.getLogger(OsbbController.class);

    @Autowired
    private OsbbService osbbService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Osbb>> createOsbb(@RequestBody Osbb osbb) {
        logger.info("Create osbb:  " + osbb);
        osbb.setLogo(osbb.getLogo());
        Osbb savedOsbb = osbbService.addOsbb(osbb);
        return new ResponseEntity<>(addResourceLinkToOsbb(savedOsbb), HttpStatus.OK);
    }

    @RequestMapping(value = "/dto/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<OsbbDTO>> getOsbbDTOById(@PathVariable("id") Integer osbbId) {
        logger.info("Get one osbbDto by id: " + osbbId);
        Osbb osbb = osbbService.getOsbb(osbbId);
        return new ResponseEntity<>(addResourceLinkToOsbb(OsbbDTOMapper.mapOsbbEntityToDTO(osbb)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Osbb>> getOsbbById(@PathVariable("id") Integer osbbId) {
        logger.info("Get one osbb by id: " + osbbId);
        Osbb osbb = osbbService.getOsbb(osbbId);
        return new ResponseEntity<>(addResourceLinkToOsbb(osbb), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Osbb>>> getOsbbByNameContaining(@PathVariable("name") String osbbName) {
        logger.info("Get all osbb: " +  osbbName);
        List<Osbb> osbbList = osbbService.findByNameContaining(osbbName);
        final List<Resource<Osbb>> resourceOsbbList = new ArrayList<>();
        
        for(Osbb o: osbbList) {
            resourceOsbbList.add(addResourceLinkToOsbb(o));
        }
        
        return new ResponseEntity<>(resourceOsbbList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Osbb>>> getAllOsbbByOrder(
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder) {
        logger.info("Get all osbb by order: '" + sortedBy + "'. AscOrder: '" + ascOrder + "'.");
        List<Osbb> osbbList = osbbService.getAllByOrder(sortedBy, ascOrder);
        final List<Resource<Osbb>> resourceOsbbList = new ArrayList<>();
        
        for(Osbb o: osbbList) {
            resourceOsbbList.add(addResourceLinkToOsbb(o));
        }
        
        return new ResponseEntity<>(resourceOsbbList, HttpStatus.OK);
    }

    @RequestMapping(value = "status/{available}",method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Osbb>>> getByActive(@PathVariable("available") Boolean available) {
        logger.info("Get osbb by active: " + available);
        List<Osbb> osbbList = osbbService.findByAvailable(available);
        final List<Resource<Osbb>> resourceOsbbList = new ArrayList<>();
        
        for(Osbb o: osbbList) {
            resourceOsbbList.add(addResourceLinkToOsbb(o));
        }
        
        return new ResponseEntity<>(resourceOsbbList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<Osbb>> updateOsbb(@RequestBody Osbb osbb) {
        logger.info("Update osbb with id: " + osbb.getOsbbId());
        Osbb updatedOsbb = osbbService.updateOsbb(osbb);
        return new ResponseEntity<>(addResourceLinkToOsbb(updatedOsbb), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Osbb>> deleteOsbb(@PathVariable("id") Integer id) {
        logger.info("Delete osbb with id: " + id );
        osbbService.deleteOsbb(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Resource<Osbb> addResourceLinkToOsbb(Osbb osbb) {
        if (osbb == null) {
            return null;
        }
        
        Resource<Osbb> osbbResource = new Resource<>(osbb);
        osbbResource.add(linkTo(methodOn(OsbbController.class)
                .getOsbbById(osbb.getOsbbId()))
                .withSelfRel());
        return osbbResource;
    }

    private Resource<OsbbDTO> addResourceLinkToOsbb(OsbbDTO osbb) {
        if (osbb == null) {
            return null;
        }
        
        Resource<OsbbDTO> osbbResource = new Resource<>(osbb);
        osbbResource.add(linkTo(methodOn(OsbbController.class)
                .getOsbbById(osbb.getOsbbId()))
                .withSelfRel());
        return osbbResource;
    }
    
}
