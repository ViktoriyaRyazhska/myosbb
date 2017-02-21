package com.softserve.osbb.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="chat")
public class Chat {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chatId;
	
	 @Column(name = "message")
    private String message;
	 
	 @Column(name = "message_time")
    private Timestamp messageTime;
	 
	
	 @ManyToOne
	 @JoinColumn(name = "user_id", referencedColumnName = "user_id")
     private User user;
    
	 
	 public Chat(){
		 
	 }
	 
	public Chat(String message, Timestamp messageTime, User user) {
		super();
		this.message = message;
		this.messageTime = messageTime;
		this.user = user;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
    public String toString() {
        return "Message{" +
                "messageId=" + chatId +
                ", message='" + message + '\'' +
                ", time=" + messageTime +
                 ", user=" + user+
                '}';
    }
}
