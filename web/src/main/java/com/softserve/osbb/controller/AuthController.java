package com.softserve.osbb.controller;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

/**
 * Created by nazar.dovhyy on 30.10.2016.
 */
@CrossOrigin
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
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
        if (foundUser != null) throw new UserAlreadyExistsException("user already exists");
        User registeredUser = registrationService.registrate(
                userRegistrationDTOMapper.mapDTOToEntity(userRegistrationDTO)
        );
        return new ResponseEntity<>(UserDTOMapper.mapUserEntityToDTO(registeredUser), HttpStatus.OK);
    }


    @RequestMapping(value = "/registration/osbb", method = RequestMethod.POST)
    public ResponseEntity<Osbb> putUser(@RequestBody OsbbRegistrationDTO osbbRegistrationDTO) {
        logger.info("registering new osbb");
        Osbb registeredOsbb = registrationService.registrate(
                osbbRegistrationxDTOMapper.mapDTOToEntity(osbbRegistrationDTO)
        );
        return new ResponseEntity<>(registeredOsbb, HttpStatus.OK);
    }


    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "user already exists")
    private static class UserAlreadyExistsException extends RuntimeException {

        UserAlreadyExistsException(String message) {
            super(message);
        }
    }

}
