package com.softserve.osbb.service;

import javax.transaction.Transactional;
import java.util.List;

import com.softserve.osbb.model.Chat;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Message;

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

	void deleteHalf();

	long countChatMessages();

}
