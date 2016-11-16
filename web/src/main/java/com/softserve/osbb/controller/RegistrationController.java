/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.OsbbRegistrationDTO;
import com.softserve.osbb.dto.UserDTO;
import com.softserve.osbb.dto.UserRegistrationDTO;
import com.softserve.osbb.dto.mappers.OsbbRegistrationDTOMapper;
import com.softserve.osbb.dto.mappers.UserDTOMapper;
import com.softserve.osbb.dto.mappers.UserRegistrationDTOMapper;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.RegistrationService;
import com.softserve.osbb.service.UserService;

/**
 * Handles registration requests.
 * 
 * Created by nazar.dovhyy on 30.10.2016.
 * @version 1.1
 * @since 16.112016
 */
@CrossOrigin
@RestController
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private UserRegistrationDTOMapper userRegistrationDTOMapper;
    
    @Autowired
    private OsbbRegistrationDTOMapper osbbRegistrationxDTOMapper;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> putUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User foundUser = userService.findUserByEmail(userRegistrationDTO.getEmail());
        
        if (foundUser != null) {
            throw new UserAlreadyExistsException("user already exists");
        }
        
        User registeredUser = registrationService.registrate(
                userRegistrationDTOMapper.mapDTOToEntity(userRegistrationDTO));
        
        return new ResponseEntity<>(UserDTOMapper.mapUserEntityToDTO(registeredUser), HttpStatus.OK);
    }

    @RequestMapping(value = "/registration/osbb", method = RequestMethod.POST)
    public ResponseEntity<Osbb> putUser(@RequestBody OsbbRegistrationDTO osbbRegistrationDTO) {
        logger.info("registering new osbb");
        Osbb registeredOsbb = registrationService.registrate(
                osbbRegistrationxDTOMapper.mapDTOToEntity(osbbRegistrationDTO));
        return new ResponseEntity<>(registeredOsbb, HttpStatus.OK);
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "user already exists")
    private static class UserAlreadyExistsException extends RuntimeException {

        UserAlreadyExistsException(String message) {
            super(message);
        }
    }

}
