package com.softserve.osbb.controller;


import com.softserve.osbb.model.Ticket;

import com.softserve.osbb.model.User;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.TicketService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.impl.MailSenderImpl;
import com.softserve.osbb.service.utils.Sha256Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.HashMap;

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
    @Value("${service.serverpath}")
    String serverUrl;

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
        }catch(NumberFormatException e){
          logger.warn("Validate.Forgot.Password.Key: Exception during parsing"+e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }

    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/sendEmailAssign", method = RequestMethod.POST)
    public HttpStatus sendEmailAssignTicket(@RequestBody Integer ticketId) throws MessagingException {
        Ticket ticket = ticketService.findOne(ticketId);
        logger.info("Send sendEmailAssignTicket " + ticket.getUser().getEmail());

        if (ticket.getAssigned().getEmail() != null) {
            sender.send(ticket.getAssigned().getEmail(), "My-osbb. You selected responsible.", "Name ticket: " + ticket.getName() +
                    " To see more information, click on link: " + "\n"
                    + serverUrl + "home/user/ticket" + ticket.getTicketId());
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
                    " To see more information, click on link: " + serverUrl +
                    "home/user/ticket" + ticket.getTicketId());
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.NOT_FOUND;

    }
}





