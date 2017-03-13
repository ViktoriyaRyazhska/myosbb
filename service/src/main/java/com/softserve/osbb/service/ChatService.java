package com.softserve.osbb.service;

import java.io.File;
import java.util.List;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.ListMessages;

public interface ChatService {

	void delete(Integer id);

	void delete(Chat chat);

	void deleteAll();

	Chat update(Chat chat);

	Chat findOne(Integer id);

	Chat findOne(String id);

	Chat save(Chat chat);
	
	List<Chat> findAll();
	
	void deleteHalf();
	
	void getHalf();
	
	long countChatMessages();
	
	void saveToDrive(List<Chat> list, ListMessages listOfMessages, Chat chat);

}
