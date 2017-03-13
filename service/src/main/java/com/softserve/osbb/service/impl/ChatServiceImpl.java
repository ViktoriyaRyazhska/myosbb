package com.softserve.osbb.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.ListMessages;
import com.softserve.osbb.repository.ChatRepository;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.GoogleDriveService;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

	@Autowired
	ChatRepository chatRepository;
	
	@Autowired
	private GoogleDriveService driveService;

	@Override
	public void delete(Chat chat) {
		chatRepository.delete(chat.getChatId());
	}

	@Override
	public void deleteAll() {
		chatRepository.deleteAll();
	}

	@Override
	public Chat update(Chat chat) {
		return chatRepository.save(chat);
	}

	@Override
	public Chat findOne(Integer id) {
		return chatRepository.findOne(id);
	}

	@Override
	public Chat findOne(String id) {
		try {
			return chatRepository.findOne(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}

	@Override
	public void delete(Integer id) {
		chatRepository.delete(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

	@Transactional
	@Override
	public void deleteHalf() {
		chatRepository.deleteHalf();
	}
	
	@Transactional
	@Override
	public void getHalf(){
		chatRepository.getHalf();
	}

	@Override
	public long countChatMessages() {
		return chatRepository.count();
	}

	@Override
	public void saveToDrive(List<Chat> list, ListMessages listOfMessages, Chat chat) {
		
		try {
			File file = new File("file.xml");
			
			InputStream input = driveService.getInput("1gswZA7t0tjwo1PAcjHBbfpI7flMLInkhrEX-8iVdnmne");
		
			JAXBContext jaxbContext = JAXBContext.newInstance(ListMessages.class, Chat.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			if (file.length() != 0) {
				listOfMessages = (ListMessages) jaxbUnmarshaller.unmarshal(input);
				for(Chat t: listOfMessages.getMessages()){
					System.out.println(t);
				}
				listOfMessages.getMessages().add(chat);
			}
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(listOfMessages, file);
			driveService.delete("1gswZA7t0tjwo1PAcjHBbfpI7flMLInkhrEX-8iVdnmne");
			driveService.insertChatFile("Chat messages", file.getName(), file);

		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
