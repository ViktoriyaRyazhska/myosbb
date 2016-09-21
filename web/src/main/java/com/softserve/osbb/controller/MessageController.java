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
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    SettingsService settingsService;

    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.POST)
    public ResponseEntity<Resource<Message>> createMessage(@RequestBody Message message,
                                                           @PathVariable("id") Integer ticketId) {
        Resource<Message> messageResource;
        try {
            User user = userService.findOne(message.getUser().getUserId());
            message.setUser(user);

            Ticket ticket = ticketService.findOne(ticketId);
            message.setTicket(ticket);

            Settings settings = settingsService.findByUser(ticket.getUser());
            if (settings.getComment()) {
                Notice notice = new Notice(ticket.getUser(), message.getUser().getFirstName() + " " + message.getUser().getLastName(),
                        message.getMessage(), "home/user/ticket/" + ticketId, NoticeType.MESSAGE);
                notice.setTime(new Timestamp(new Date().getTime()));
                noticeService.save(notice);
            }

            message.setTime(new Timestamp(new Date().getTime()));
            message = messageService.save(message);

            messageResource = addResourceLinkToMessage(message);
            logger.info("Saving message object " + message.getMessageId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
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
                Notice notice = new Notice(parentMessage.getUser(), message.getUser().getFirstName() + " " + message.getUser().getLastName(),
                        message.getMessage(), "home/user/ticket/" + parentMessage.getTicket().getTicketId(), NoticeType.ANSWER);
                notice.setTime(new Timestamp(new Date().getTime()));
                noticeService.save(notice);
            }
            message = messageService.save(message);


            messageResource = addResourceLinkToMessage(message);
            logger.info("Saving answer object " + message.getMessageId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Message>> getMessageById(@PathVariable("id") Integer messageId) {

        logger.info("Get message by id: " + messageId);
        Message message = messageService.findOne(messageId);
        Resource<Message> messageResource = addResourceLinkToMessage(message);
        messageResource.add(linkTo(methodOn(MessageController.class).getMessageById(messageId)).withSelfRel());
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<Message>> updateMessage(@RequestBody Message message) {

        logger.info("Updating message by id: " + message);
        message = messageService.update(message);
        Resource<Message> messageResource = new Resource<>(message);
        messageResource.add(linkTo(methodOn(MessageController.class).getMessageById(message.getMessageId())).withSelfRel());
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

//    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
//    public ResponseEntity<List<Resource<Message>>>  getMessageByTicketId(@PathVariable("id")Integer ticketId) {
//
//        logger.info("message by ticket: " + ticketId);
//        Ticket ticket = ticketService.findOne(ticketId);
//        List<Message> messages = messageService.findMessagesByTicket(ticket);
//        List<Resource<Message>> resourceMessageList = new ArrayList<>();
//        for (Message e : messages) {
//            Resource<Message> messageResource = new Resource<>(e);
//            messageResource.add(linkTo(methodOn(MessageController.class)
//                    .getMessageById(e.getMessageId()))
//                    .withSelfRel());
//            resourceMessageList.add(messageResource);
//        }
//        return new ResponseEntity<>(resourceMessageList, HttpStatus.OK);
//
//    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.POST)
    public ResponseEntity<MessagePageDataObject> getMessagesByTicketId(@RequestBody PageRequestGenerator.PageRequestHolder requestHolder,
                                                                       @PathVariable("id") Integer ticketId) {
        logger.info("get all messages by page: " + requestHolder.getPageNumber());
        final PageRequest pageRequest = new PageRequestGenerator(requestHolder)
                .toPageRequest();
        Ticket ticket = ticketService.findOne(ticketId);
        List<MessageDTO> messageDTO = new ArrayList<>();
        Page<Message> messagesByPage = messageService.findMessagesByTicket(ticket, pageRequest);
        logger.info("get all messages by page: " + messagesByPage.getTotalElements());

        Iterator iter = messagesByPage.iterator();
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
        logger.info("get all messages by page: " + messagesDTOByPage.getContent().toString());

        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
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
