package com.softserve.osbb.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.softserve.osbb.model.Greeting;
import com.softserve.osbb.model.Message;

import java.sql.Timestamp;


@Controller
public class ChatController {
	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay    
        return new Greeting(message.getContent(),new Timestamp(System.currentTimeMillis()));
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
