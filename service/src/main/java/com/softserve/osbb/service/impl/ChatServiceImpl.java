package com.softserve.osbb.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.repository.ChatRepository;
import com.softserve.osbb.service.ChatService;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatRepository chatRepository;
	
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

}