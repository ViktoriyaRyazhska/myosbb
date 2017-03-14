package com.softserve.osbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.MessageService;
import com.softserve.osbb.model.ChatMessage;
import com.softserve.osbb.model.ListMessages;
import com.softserve.osbb.service.ChatService;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


@Controller
@RestController
@CrossOrigin
@RequestMapping("/restful/chat")
public class ChatController {

	
	@Autowired
    private ChatService chatService;
	

	@SendTo("/topic/greetings")
	public Chat chat(ChatMessage message) throws Exception {
		Thread.sleep(1000);
		Chat chat = new Chat(message.getMessage(), new Timestamp(System.currentTimeMillis()));
		chatService.save(chat);
		List<Chat> list = new ArrayList<>();
		list.add(chat);
		ListMessages listOfMessages = new ListMessages();
		listOfMessages.setMessages(list);
		
		if(chatService.countChatMessages()>7)chatService.deleteHalf();

		try {

			File file = new File("D:\\file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ListMessages.class, Chat.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			if (file.length() != 0) {
				listOfMessages = (ListMessages) jaxbUnmarshaller.unmarshal(file);
				listOfMessages.getMessages().add(chat);
			}

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(listOfMessages, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return chatService.save(chat);
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Chat>> findAll() {
        List<Chat> messages = chatService.findAll();
		
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
	
	

}