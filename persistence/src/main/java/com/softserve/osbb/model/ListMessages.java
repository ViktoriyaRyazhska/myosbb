package com.softserve.osbb.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "messages")
@XmlAccessorType (XmlAccessType.FIELD)
public class ListMessages {
	
	@XmlElement(name = "chat")
    private List<Chat> messages = new ArrayList<>();
 
    public List<Chat> getMessages() {
		return messages;
    }
 
    public void setMessages(List<Chat> messages) {
        this.messages = messages;
    }

}
