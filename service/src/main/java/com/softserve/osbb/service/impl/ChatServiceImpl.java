package com.softserve.osbb.service.impl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
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
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChatServiceImpl implements ChatService{

	private static String driveFolderId ="1VQ-fkh7WmLYsx_gYvqmAWS7QWj6h";
	
	private static String driveFileName = "file.xml";
	
	@Autowired
	ChatRepository chatRepository;
	
	
	@Autowired
	private GoogleDriveService googleDriveService;
	
	@Autowired
	private ChatService chatService;

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

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Chat saveAndFlush(Chat chat) {
        return chatRepository.saveAndFlush(chat);
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
	public List<Chat> getHalf(){
		return chatRepository.getHalf();
	}

	@Override
	public long countChatMessages() {
		return chatRepository.count();
	}
	@Async
	@Override
	public void writeFile() throws IOException, JAXBException {
		File file = new File(driveFileName);
		InputStream input = googleDriveService.getInput(googleDriveService.findByName(driveFileName, driveFolderId).getId());
		ListMessages listOfMessages = new ListMessages();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ListMessages.class, Chat.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		listOfMessages = (ListMessages) jaxbUnmarshaller.unmarshal(input);
		List<Chat> list = listOfMessages.getMessages();
		boolean isAdded=list.addAll(chatService.getHalf());
		listOfMessages.setMessages(list);

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(listOfMessages, file);
		
		googleDriveService.delete(googleDriveService.findByName(driveFileName,  driveFolderId).getId());
		googleDriveService.insertChatFile("Chat messages", file.getName(), file);
		
	}	

}
