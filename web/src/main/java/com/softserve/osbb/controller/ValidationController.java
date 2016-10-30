package com.softserve.osbb.controller;


import com.softserve.osbb.dto.OsbbRegistrationDTO;
import com.softserve.osbb.dto.UserDTO;
import com.softserve.osbb.dto.UserRegistrationDTO;
import com.softserve.osbb.dto.mappers.OsbbRegistrationDTOMapper;
import com.softserve.osbb.dto.mappers.UserRegistrationDTOMapper;
import com.softserve.osbb.dto.mappers.UserDTOMapper;
import com.softserve.osbb.model.Osbb;

import com.softserve.osbb.model.User;
import com.softserve.osbb.service.*;
import com.softserve.osbb.service.impl.MailSenderImpl;
import com.softserve.osbb.service.utils.Sha256Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.util.HashMap;

/**
 * Created by cavayman on 03.08.2016.
 */

@CrossOrigin
@RestController
public class ValidationController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OsbbService osbbService;
    @Value("${service.serverpath}")
    private String serverUrl;

    @RequestMapping(value = "/validEmail", method = RequestMethod.POST)
    public HttpStatus validateEmail(@RequestBody String email) {
        if (userService.findUserByEmail(email) != null) {
            System.out.println(userService.findUserByEmail(email));
            return HttpStatus.FOUND;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Autowired
    private MailSenderImpl sender;

    @Autowired
    private Sha256Encoder sha256Encoder;

    @RequestMapping(value = "/sendEmailMail", method = RequestMethod.POST)
    public HttpStatus sendEmailOnMail(@RequestBody String email) throws MessagingException {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            sha256Encoder.setSecretKeyForUser(user.getUserId());
            sender.send(email, "My-osbb.Your forgoten password", "Hello there friend! here is your url to change your pass:"
                    + serverUrl
                    + "forgotPass;id=" + user.getUserId() + ";key=" + sha256Encoder.getSecretKeyForEmail(user.getUserId()) + ";");
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(value = "/validateForgotPasswordKey", method = RequestMethod.POST)
    public HttpStatus validateKey(@RequestBody HashMap<String, String> requestParams) {
        try {
            Integer id = Integer.parseInt(requestParams.get("id"));
            String key = requestParams.get("key");
            return sha256Encoder.validateSecretKeyForEmail(id, key) ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        } catch (NumberFormatException e) {
            logger.warn("Validate.Forgot.Password.Key: Exception during parsing" + e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }


}





