package com.softserve.osbb.service;

import java.util.List;

import com.softserve.osbb.model.Chat;

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
	
	long countChatMessages();

}
