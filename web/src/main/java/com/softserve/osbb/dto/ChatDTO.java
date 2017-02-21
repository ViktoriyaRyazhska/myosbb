package com.softserve.osbb.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.User;

public class ChatDTO {
	
	private Integer chatId;
    private String message;
    private Timestamp messageTime;
    private User user;
    private Collection<Chat> answers = new ArrayList<>();
	public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collection<Chat> getAnswers() {
		return answers;
	}
	public void setAnswers(Collection<Chat> answers) {
		this.answers = answers;
	}
	
	@Override
    public String toString() {
        return "ChatDTO{" +
                "chatId=" + chatId +
                ", message='" + message + '\'' +
                ", messageTime=" + messageTime +
                ", user=" + user +
                ", answers" + answers +
                '}';
    }
}
