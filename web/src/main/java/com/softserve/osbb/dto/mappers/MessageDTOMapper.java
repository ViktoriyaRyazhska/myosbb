/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.User;

import java.util.List;

/**
 * Created by Kris on 31.08.2016.
 */
public class MessageDTOMapper {

    public static MessageDTO mapMessageEntityToDTO(Message message, List<Message> answerList) {
        MessageDTO messageDTO = new MessageDTO();
        
        if(message != null) {
            messageDTO.setMessageId(message.getMessageId());
            messageDTO.setParentId(message.getParentId());
            messageDTO.setTicket(message.getTicket());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setAnswers(answerList);
            messageDTO.setTime(message.getTime());
            messageDTO.setUser(message.getUser());
        }
        return messageDTO;
    }

    public static Message mapMessageDTOtoEntity(MessageDTO messageDTO, User user) {
        Message message = new Message();
        
        if(message != null) {
            message.setMessageId(messageDTO.getMessageId());
            message.setParentId(messageDTO.getParentId());
            message.setTicket(messageDTO.getTicket());
            message.setMessage(messageDTO.getMessage());
            message.setTime(messageDTO.getTime());
            message.setUser(user);
        }
        return message;
    }

}
