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

	@Autowired
	private ChatService chatService;

	@MessageMapping("/chat")
	@SendTo("/topic/greetings")
	public Chat chat(ChatMessage message, ChatMessage user) throws Exception {
		Chat chat = new Chat(message.getMessage(), new Timestamp(System.currentTimeMillis()), user.getUser());
		chatService.save(chat);		

		if (chatService.countChatMessages() >= 6) {
			chatService.deleteHalf();
		}
		if (chatService.countChatMessages() >= 5) {
			try {
				chatService.writeFile();
			} catch (IOException | JAXBException e) {
				e.printStackTrace();
			}
		}
		return chatService.save(chat);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Chat>> findAll() {
		List<Chat> messages = chatService.findAll();
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

}