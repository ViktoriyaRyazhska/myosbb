package com.softserve.osbb.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.service.ChatService;


@RestController
@CrossOrigin
@RequestMapping("/restful/chat")
public class ChatController {
	
	@Autowired
    private ChatService chatService;
 
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
 
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Chat>> createChat(@RequestBody Chat chat) {        
        
        chat.setMessageTime(new Timestamp(new Date().getTime()));
        Chat savedChat = chatService.save(chat);

        logger.info("Saving message object " + chat.getChatId());
  return new ResponseEntity<>(addResourceLinkToChat(savedChat), HttpStatus.OK);
    }
 
 @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Chat>> getChatById(@PathVariable("id") Integer chatId) {
        logger.info("Get one chat by id: " + chatId);
        Chat chat = chatService.findOne(chatId);
        return new ResponseEntity<>(addResourceLinkToChat(chat), HttpStatus.OK);
    }
 
 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Chat>> deleteChat(@PathVariable("id") Integer id) {
        logger.info("Delete chat with id: " + id );
        chatService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 
 private Resource<Chat> addResourceLinkToChat(Chat chat) {
        if (chat == null) {
            return null;
        }
        
        Resource<Chat> chatResource = new Resource<>(chat);
        chatResource.add(linkTo(methodOn(ChatController.class)
                .getChatById(chat.getChatId()))
                .withSelfRel());
        return chatResource;
    }
	
}
