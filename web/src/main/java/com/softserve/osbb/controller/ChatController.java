package com.softserve.osbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.model.ChatMessage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.JAXBException;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/restful/chat")
public class ChatController {

	private static final int DELETION_ZONE = 6;
	
	private static final int WRITING_ZONE = 5;
	
	@Autowired
	private ChatService chatService;

	@MessageMapping("/chat")
	@SendTo("/topic/greetings")
	public Chat chat(ChatMessage message, ChatMessage user) throws Exception {
		Chat chat = new Chat(message.getMessage(), new Timestamp(System.currentTimeMillis()), user.getUser());
	    Chat savedChat = chatService.save(chat);		

		if (chatService.countChatMessages() >= DELETION_ZONE) {
			chatService.cleanDB();
		}
		if (chatService.countChatMessages() >= WRITING_ZONE) {
			try {
				chatService.writeFile();
			} catch (IOException | JAXBException e) {
				e.printStackTrace();
			}
		}
		return savedChat;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Chat>> findAll() {
		List<Chat> messages = chatService.findAll();
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

}