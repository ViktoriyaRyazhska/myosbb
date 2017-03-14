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
import com.softserve.osbb.model.ListMessages;
import com.softserve.osbb.service.DriveService;
import com.softserve.osbb.service.GoogleDriveService;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/restful/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private GoogleDriveService googleDriveService;

	@Autowired
	private DriveService driveService;

	@MessageMapping("/chat")
	@SendTo("/topic/greetings")
	public Chat chat(ChatMessage message) throws Exception {
		Chat chat = new Chat(message.getMessage(), new Timestamp(System.currentTimeMillis()));
		chatService.save(chat);		

		if (chatService.countChatMessages() == 21){
			chatService.deleteHalf();
		}
		if (chatService.countChatMessages() == 20) {
			File file = new File("file.xml");
			String driveId = driveService.findAll().get(0).getFile();
			InputStream input = googleDriveService.getInput(driveId);
			
			ListMessages listOfMessages = new ListMessages();
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ListMessages.class, Chat.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			listOfMessages = (ListMessages) jaxbUnmarshaller.unmarshal(input);
			listOfMessages.setMessages(chatService.getHalf());

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(listOfMessages, file);
			
			googleDriveService.delete(driveId);
			driveService.deleteAll();
			googleDriveService.insertChatFile("Chat messages", file.getName(), file);
		}
		return chatService.save(chat);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Chat>> findAll() {
		List<Chat> messages = chatService.findAll();
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}	

}