/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.dto.mappers.MessageDTOMapper;
import com.softserve.osbb.model.*;
import com.softserve.osbb.model.enums.NoticeType;
import com.softserve.osbb.service.*;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import com.softserve.osbb.util.paging.impl.MessagePageDataObject;
import com.softserve.osbb.util.resources.impl.EntityResourceList;
import com.softserve.osbb.util.resources.impl.MessageResourceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Kris on 12.07.2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/message")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    SettingsService settingsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Message>> createMessage(@RequestBody Message message,
                                                           @RequestParam(value = "ticket") Integer ticketId) {
        Resource<Message> messageResource;
        
        try {
            Ticket ticket = ticketService.findOne(ticketId);
            message.setTicket(ticket);
            Settings settings = settingsService.findByUser(ticket.getUser());
            
            if (settings.getComment()) {
                Notice notice = new Notice(ticket.getUser(), 
                        message.getUser().getFirstName() + " " + message.getUser().getLastName(),
                        message.getMessage(), "ticket/" + ticketId, NoticeType.MESSAGE);
                notice.setTime(new Timestamp(new Date().getTime()));
                noticeService.save(notice);
            }

            message.setTime(new Timestamp(new Date().getTime()));
            message = messageService.save(message);

            messageResource = addResourceLinkToMessage(message);
            logger.info("Saving message object " + message.getMessageId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResponseEntity<Resource<Message>> createAnswer(@RequestBody Message message) {
        Resource<Message> messageResource;
        
        try {
            message.setTime(new Timestamp(new Date().getTime()));
            Message parentMessage = messageService.findOne(message.getParentId());
            Settings settings = settingsService.findByUser(message.getUser());
            
            if (settings.getAnswer()) {
                Notice notice = new Notice(parentMessage.getUser(), 
                        message.getUser().getFirstName() + " " + message.getUser().getLastName(),
                        message.getMessage(), "ticket/" + parentMessage.getTicket().getTicketId(), 
                        NoticeType.ANSWER);
                notice.setTime(new Timestamp(new Date().getTime()));
                noticeService.save(notice);
            }
            
            message = messageService.save(message);
            messageResource = addResourceLinkToMessage(message);
            logger.info("Saving answer object " + message.getMessageId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public ResponseEntity<MessagePageDataObject> getMessagesByTicketId(
            @RequestBody PageRequestGenerator.PageRequestHolder requestHolder,                                                                       
            @RequestParam(value = "ticket") Integer ticketId) {
        logger.info("get comments for ticket: " + ticketId);
        final PageRequest pageRequest = new PageRequestGenerator(requestHolder).toPageRequest();
        Ticket ticket = ticketService.findOne(ticketId);
        List<MessageDTO> messageDTO = new ArrayList<>();
        Page<Message> messagesByPage = messageService.findMessagesByTicket(ticket, pageRequest);

        Iterator<Message> iter = messagesByPage.iterator();
        while (iter.hasNext()) {
            Message mess = (Message) iter.next();
            List<Message> answers = messageService.getAnswers(mess.getMessageId());
            messageDTO.add(MessageDTOMapper.mapMessageEntityToDTO(mess, answers));
        }
        
        Page<MessageDTO> messagesDTOByPage = new PageImpl<>(messageDTO);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(messagesDTOByPage);
        EntityResourceList<MessageDTO> messageResourceList = new MessageResourceList();
        messagesDTOByPage.forEach((message) -> messageResourceList.add(toResource(message)));
        MessagePageDataObject pageCreator = setUpPageCreator(pageSelector, messageResourceList);
        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
    }

    private Resource<Message> addResourceLinkToMessage(Message message) {
        if (message == null) {
            return null;
        }
        
        Resource<Message> messageResource = new Resource<>(message);
        messageResource.add(linkTo(methodOn(MessageController.class)
                .getMessageById(message.getMessageId()))
                .withSelfRel());
        return messageResource;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<Message>> updateMessage(@RequestBody Message message) {
        logger.info("Updating message by id: " + message);
        message = messageService.update(message);
        Resource<Message> messageResource = new Resource<>(message);
        messageResource.add(linkTo(methodOn(MessageController.class).getMessageById(message.getMessageId())).withSelfRel());
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Message>> getMessageById(@PathVariable("id") Integer messageId) {
        logger.info("Get message by id: " + messageId);
        Message message = messageService.findOne(messageId);
        Resource<Message> messageResource = addResourceLinkToMessage(message);
        messageResource.add(linkTo(methodOn(MessageController.class).getMessageById(messageId)).withSelfRel());
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteMessageById(@PathVariable("id") Integer messageId) {
        logger.info("Removing answers and message by id: " + messageId);
        List<Message> answers = messageService.getAnswers(messageId);
        messageService.delete(messageId);
        messageService.delete(answers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteAll() {
        logger.info("Removing all messages");
        messageService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private MessagePageDataObject setUpPageCreator(PageRequestGenerator.PageSelector pageSelector, List<Resource<MessageDTO>> resourceList) {
        MessagePageDataObject pageCreator = new MessagePageDataObject();
        pageCreator.setRows(resourceList);
        pageCreator.setCurrentPage(Integer.valueOf(pageSelector.getCurrentPage()).toString());
        pageCreator.setBeginPage(Integer.valueOf(pageSelector.getBegin()).toString());
        pageCreator.setEndPage(Integer.valueOf(pageSelector.getEnd()).toString());
        pageCreator.setTotalPages(Integer.valueOf(pageSelector.getTotalPages()).toString());
        return pageCreator;
    }
}
