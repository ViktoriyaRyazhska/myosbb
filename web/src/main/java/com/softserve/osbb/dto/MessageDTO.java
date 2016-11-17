/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto;

import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Kris on 31.08.2016.
 */
public class MessageDTO {
    
    private Integer messageId;
    private Integer parentId;
    private String message;
    private Timestamp time;
    private Ticket ticket;
    private User user;
    private Collection<Message> answers = new ArrayList<>();

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Message> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Message> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "messageId=" + messageId +
                ", parentId=" + parentId +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", ticket=" + ticket +
                ", user=" + user +
                ", answers" + answers +
                '}';
    }
    
}

