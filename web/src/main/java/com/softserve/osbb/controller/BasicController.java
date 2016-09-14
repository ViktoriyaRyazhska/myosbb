package com.softserve.osbb.controller;


import com.softserve.osbb.model.Ticket;

import com.softserve.osbb.model.User;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.TicketService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.impl.MailSenderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cavayman on 03.08.2016.
 */

@CrossOrigin
@RestController
public class BasicController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    OsbbService osbbService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public User putUser(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null)
            return userService.findUserByEmail(user.getEmail());
        logger.info("Saving user, sending to service");
        return userService.save(user);
    }

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

    private HashMap<Integer, String> secretKeysForForgotPassword = new HashMap<Integer, String>();

    @RequestMapping(value = "/sendEmailMail", method = RequestMethod.POST)
    public HttpStatus sendEmailOnMail(@RequestBody String email, HttpServletRequest httpServletRequest) throws MessagingException {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            setSecretKeyForUser(user.getUserId());
            sender.send(email, "My-osbb.Your forgoten password", "Hello there friend! here is your url to change your pass:" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
                    + "/myosbb/forgotPass;id=" + user.getUserId() + ";key=" + getSecretKeyForEmail(user.getUserId())+";");
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(value = "/validateForgotPasswordKey", method = RequestMethod.POST)
    public HttpStatus validateKey(@RequestBody HashMap<String,String> requestParams)  {
        System.out.println("Asdasd"+requestParams);
          System.out.println(this.secretKeysForForgotPassword);
        return validateSecretKeyForEmail(Integer.parseInt(requestParams.get("id")),requestParams.get("key"))? HttpStatus.FOUND:HttpStatus.NOT_FOUND;
    }


    public void setSecretKeyForUser(Integer id) {
        if (!secretKeysForForgotPassword.containsKey(id)) {
            secretKeysForForgotPassword.put(id, getSHA256Hash(userService.getOne(id).getEmail()));
        System.out.println("Added key  to HashMap");
        }

    }

    public String getSecretKeyForEmail(Integer id) {
        return this.secretKeysForForgotPassword.containsKey(id) ? secretKeysForForgotPassword.get(id) : null;
    }

    /**
     * If hashmap contains key method returns true and deleting it.
     *
     * @param email
     * @return boolean
     */
    public Boolean validateSecretKeyForEmail(Integer email, String key) {
        if (this.secretKeysForForgotPassword.containsKey(email)) {
          System.out.println("pass contain key");
            if (secretKeysForForgotPassword.get(email).equals(key)) {
                 System.out.println("pass get equals");
                secretKeysForForgotPassword.remove(email);
                return true;
            } else return false;
        } else return false;
    }

    //These 2 methods are needet to convert data to SHA256
    private String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }


    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/sendEmailAssign", method = RequestMethod.POST)
    public HttpStatus sendEmailAssignTicket(@RequestBody Integer ticketId) throws MessagingException {
        Ticket ticket = ticketService.findOne(ticketId);
        logger.info("Send sendEmailAssignTicket " + ticket.getUser().getEmail());

        if (ticket.getAssigned().getEmail() != null) {
            sender.send(ticket.getAssigned().getEmail(), "My-osbb. You selected responsible.", "Name ticket: " + ticket.getName() +
                    " To see more information, click on link: " + "\n" +
                    "192.168.195.250:8080/myosbb/home/user/ticket" + ticket.getTicketId());
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(value = "/sendEmailState", method = RequestMethod.POST)
    public HttpStatus sendEmailStateTicket(@RequestBody Integer ticketId) throws MessagingException {

        Ticket ticket = ticketService.findOne(ticketId);
        logger.info("Send sendEmailStateTicket " + ticket.getUser().getEmail());
        if (ticket.getUser().getEmail() != null) {
            sender.send(ticket.getAssigned().getEmail(), "My-osbb. Change state of your ticket.", "Name ticket: " + ticket.getName() +
                    " New status: " + ticket.getState() + "\n" +
                    " To see more information, click on link: " +
                    "192.168.195.250:8080/myosbb/home/user/ticket" + ticket.getTicketId());
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.NOT_FOUND;

    }
}





