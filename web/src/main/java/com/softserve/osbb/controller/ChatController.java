package com.softserve.osbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.MessageService;

import java.sql.Timestamp;


@Controller

public class ChatController {
	
	
	@Autowired
    private ChatService chatService;
	
	
	@MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Chat greeting(ChatMessage message) throws Exception {
 
        return chatService.save(new Chat(message.getContent(),new Timestamp(System.currentTimeMillis()),null));
        
    }	

}

class ChatMessage {

	private String content;

	public ChatMessage() {
	}

	public ChatMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
