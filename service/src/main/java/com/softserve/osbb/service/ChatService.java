package com.softserve.osbb.service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

import com.softserve.osbb.model.Chat;

import org.springframework.stereotype.Service;

@Service
@Transactional
public interface ChatService {

	void delete(Integer id);

	void delete(Chat chat);

	void deleteAll();

	Chat update(Chat chat);

	Chat findOne(Integer id);

	Chat findOne(String id);

	Chat save(Chat chat);

	Chat saveAndFlush(Chat message);

	List<Chat> findAll();

	void cleanDB();
	
	List<Chat> getPartMessages();
	
	long countChatMessages();
	
	void writeFile() throws IOException, JAXBException;
		
	

}
