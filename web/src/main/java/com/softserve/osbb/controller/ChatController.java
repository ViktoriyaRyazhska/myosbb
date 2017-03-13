package com.softserve.osbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.ChatMessage;
import com.softserve.osbb.model.ListMessages;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.DriveService;
import com.softserve.osbb.service.GoogleDriveService;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	
	@Autowired
	private GoogleDriveService driveService;
	
	@Autowired
	private DriveService d;

//	@MessageMapping("/chat")
//	@SendTo("/topic/greetings")
//	public Chat chat(ChatMessage message) throws Exception {
//		
//		Thread.sleep(1000);
//		Chat chat = new Chat(message.getMessage(), new Timestamp(System.currentTimeMillis()));
//		chatService.save(chat);
//		File m = driveService.findByName("file.xml", "1VQ-fkh7WmLYsx_gYvqmAWS7QWj6h");
//		System.out.println(m.getId());
//		List<Chat> list = new ArrayList<>();
//		list.add(chat);
//		ListMessages listOfMessages = new ListMessages();
//		listOfMessages.setMessages(list);
//		
//		if(chatService.countChatMessages()>7)chatService.deleteHalf();
//		
//		chatService.saveToDrive(list, listOfMessages, chat);
//		return chatService.save(chat);
	
//	}
//
//}
	
	@MessageMapping("/chat")
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
			File file = new File("file.xml");
		   String driveId=d.findAll().get(0).getFile();
			InputStream input = driveService.getInput(driveId);
			JAXBContext jaxbContext = JAXBContext.newInstance(ListMessages.class, Chat.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
				listOfMessages = (ListMessages) jaxbUnmarshaller.unmarshal(input);
				for(Chat t: listOfMessages.getMessages()){
					System.out.println(t);
				}
				listOfMessages.getMessages().add(chat);
			

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(listOfMessages, file);
			driveService.delete(driveId);
			d.deleteAll();
			//if(chatService.countChatMessages()==50)
			driveService.insertChatFile("Chat messages", file.getName(), file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return chatService.save(chat);
	}

}

	
	
