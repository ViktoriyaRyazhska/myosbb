package com.softserve.osbb.dto.mappers;

import java.util.List;

import com.softserve.osbb.dto.ChatDTO;
import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.User;

public class ChatDTOMapper {
	
	 public static ChatDTO chatMessageEntityToDTO(Chat chat, List<Chat> answerList) {
	        ChatDTO chatDTO = new ChatDTO();
	        
	        if(chat != null) {
	            chatDTO.setChatId(chat.getChatId());
	            chatDTO.setMessage(chat.getMessage());
	            chatDTO.setAnswers(answerList);
	            chatDTO.setMessageTime(chat.getMessageTime());
	            chatDTO.setUser(chat.getUser());
	        }
	        return chatDTO;
	    }

	    public static Chat mapChatDTOtoEntity(ChatDTO chatDTO, User user) {
	        Chat chat= new Chat();
	        
	        if(chat != null) {
	            chat.setChatId(chatDTO.getChatId());
	            chat.setMessage(chatDTO.getMessage());
	            chat.setMessageTime(chatDTO.getMessageTime());
	            chat.setUser(user);
	        }
	        return chat;
	    }
}
