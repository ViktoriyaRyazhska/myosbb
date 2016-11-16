/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.SettingsService;
import com.softserve.osbb.service.UserService;

/**
 * Created by Kris on 16.09.2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/settings")
public class SettingsController {

    private static Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    SettingsService settingsService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Settings>> createSettings(@RequestBody Integer userId) {
        Settings setting;
        
        try {
            setting = settingsService.save(new Settings(userService.getOne(userId)));
            logger.info("Saving settings object " + setting.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(addResourceLinkToSettings(setting), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<Settings>> updateSettings(@RequestBody Settings settings) {
        Resource<Settings> settingsResource = new Resource<>(settings);
        
        try {
            settingsService.update(settings);
            logger.info("Update settings object " + settings.toString());
            settingsResource.add(linkTo(methodOn(SettingsController.class)
                    .getSettingsById(settings.getSettingsId())).withSelfRel());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(settingsResource, HttpStatus.OK);
    }

    private Resource<Settings> addResourceLinkToSettings(Settings settings) {
        Resource<Settings> settingsResource = new Resource<>(settings);
        settingsResource.add(linkTo(methodOn(SettingsController.class)
                .getSettingsById(settings.getSettingsId()))
                .withSelfRel());
        return settingsResource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource<Settings>> getSettingsByUser(@AuthenticationPrincipal Principal user) {
        User currentUser = userService.findUserByEmail(user.getName());
        Settings setting = settingsService.findByUser(currentUser);
        logger.info("Get setting by id: " + setting);
        return new ResponseEntity<>(addResourceLinkToSettings(setting), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Settings>> getSettingsById(@PathVariable("id") Integer settingsId) {
        Settings setting = settingsService.findOne(settingsId);
        logger.info("Get setting by id: " + setting.getSettingsId());
        return new ResponseEntity<>(addResourceLinkToSettings(setting), HttpStatus.OK);
    }

}
