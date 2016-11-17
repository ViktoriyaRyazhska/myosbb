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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.UserDTO;
import com.softserve.osbb.dto.mappers.UserDTOMapper;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.UserService;

/**
 * Created by cavayman on 12.07.2016.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/restful")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final List<Resource<User>> EMPTY_LIST = new ArrayList<>(0);
    
    @Autowired
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    OsbbService osbbService;

    @Autowired
    UserDTOMapper userDTOMapper;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<User>>> getAll() {
        logger.info("Getting all users from database");
        List<User> users = userService.findAll();
        List<Resource<User>> resources = new ArrayList<Resource<User>>();

        if (users.isEmpty()) {
            return new ResponseEntity<>(EMPTY_LIST, HttpStatus.OK);
        } else {
            for (User temp : users) {
                resources.add(getUserResource(temp));
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/getCurrent", method = RequestMethod.GET)
    public ResponseEntity<Resource<UserDTO>>  getCurrent(@AuthenticationPrincipal Principal user) {
        User currentUser=userService.findUserByEmail(user.getName());
        UserDTO userDTO=new UserDTO(currentUser);
        return new ResponseEntity<>(addResourceLinkToUserDTO(userDTO),HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Resource<User> getUser(@PathVariable(value = "id") String id) {
        logger.info("geting user from database with id=" + id);
        return getUserResource(userService.findOne(id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User putUser(@RequestBody User user) {
        logger.info("Saving user, sending to service");
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable Integer id, @RequestBody UserDTO user) {
        logger.info("Updating user id:" + id);
        User oldUser = userService.getOne(user.getUserId());
        User newUser = UserDTOMapper.mapUserDtoToEntity(oldUser,user);
        return userService.update(newUser);
    }
    
    @RequestMapping(value = "/user/{id}/password", method = RequestMethod.POST)
    public User updateUserPassword(@PathVariable Integer id, @RequestBody String password) {
        logger.info("Updating user id:" + id);
        User user = userService.getOne(id);
        user.setPassword(passwordEncoder.encode(password));
        return userService.save(user);
    }
    
    @RequestMapping(value = "/user/{id}/changeActivation", method = RequestMethod.POST)
    public User changeUserActivation(@PathVariable Integer id) {
        User user=userService.getOne(id);
        
        if (user.getActivated()) {
            logger.info("Deactivating User with Id:" + id);
            user.setActivated(false);
            return userService.update(user);
        } else {
            logger.info("Activating User with Id:" + id);
            user.setActivated(true);
            return userService.update(user);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public boolean deleteAllUsers(@RequestBody User user) {
        logger.warn("Deleting all Users");
        userService.deleteAll();
        return true;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public boolean deleteUserByID(@PathVariable(value = "id") Integer id) {
        logger.warn("Deleting user with id:" + id);
        userService.delete(id);
        return true;
    }

    @RequestMapping(value = "/user/userOsbb/{id}",method = RequestMethod.GET)
    public Integer getOsbbIdByUserId(@PathVariable("id")Integer userId){
        logger.info("getting osbb with user id ="+userId);
        Integer osbbid = 0;
        
        try {
            osbbid = userService.getOne(userId).getOsbb().getOsbbId();
        } catch (NullPointerException e) {
            logger.warn("user not found");
        }
        return  osbbid;
    }

    @RequestMapping(value = "/user/osbb/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<User>>> getUsersByOsbbId(@PathVariable("id")Integer osbbId) {
        logger.info("Getting users from database with osbb id" + osbbId);
        Osbb osbb = osbbService.getOsbb(osbbId);
        List<User> users = userService.getUsersByOsbb(osbb);
        
        List<Resource<User>> resources = new ArrayList<>();
            for (User temp : users) {
                resources.add(getUserResource(temp));
            }
        logger.info("Getting users from database with osbb id" + users.toArray().length);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private Resource<User> getUserResource(User user) {
        Resource<User> resource = new Resource<>(user);
        resource.add(linkTo(methodOn(UserController.class).getUser(user.getUserId().toString())).withSelfRel());
        return resource;
    }

    private Resource<UserDTO> addResourceLinkToUserDTO(UserDTO user) {
        if (user == null) {
            return null;
        }
        
        Resource<UserDTO> userResource = new Resource<>(user);
        userResource.add(linkTo(methodOn(UserController.class)
                .getUser(user.getUserId().toString()))
                .withSelfRel());
        return userResource;
    }
    
}
