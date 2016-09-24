package com.softserve.osbb.controller;


import com.softserve.osbb.dto.PageParams;
import com.softserve.osbb.dto.TicketDTO;
import com.softserve.osbb.dto.mappers.TicketDTOMapper;
import com.softserve.osbb.model.*;
import com.softserve.osbb.model.enums.NoticeType;
import com.softserve.osbb.model.enums.TicketState;
import com.softserve.osbb.service.*;
import com.softserve.osbb.util.paging.PageDataUtil;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import com.softserve.osbb.util.paging.impl.TicketPageDataObject;
import com.softserve.osbb.util.resources.impl.EntityResourceList;
import com.softserve.osbb.util.resources.impl.TicketResourceList;
import com.softserve.osbb.service.impl.MailSenderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Kris on 13.07.2016.
 */

@RestController
@CrossOrigin
@RequestMapping("/restful/ticket")
public class TicketController {

    private static Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Value("${service.serverpath}")
    String serverUrl;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MailSenderImpl sender;

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Ticket>> createTicket(@RequestBody Ticket ticket) {
        Resource<Ticket> ticketResource;
        try {
            logger.info("Saving ticket object att" + ticket.getAttachments());

            User user = userService.findOne(ticket.getUser().getUserId());
            ticket.setUser(user);

            User assigned = userService.findOne(ticket.getAssigned().getUserId());
            ticket.setAssigned(assigned);

            ticket.setTime(new Timestamp(new Date().getTime()));
            ticket.setStateTime(new Timestamp(new Date().getTime()));

            Settings settings = settingsService.findByUser(ticket.getUser());
            if (settings.getAssigned()) {
                Notice notice = new Notice(assigned, ticket.getName(), "home/user/ticket/" + ticket.getTicketId(), NoticeType.TO_ASSIGNED);
                noticeService.save(notice);
            }
            ticket = ticketService.save(ticket);
            logger.info("Saving ticket object " + ticket.toString());
            ticketResource = addResourceLinkToTicket(ticket);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ticketResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Resource<TicketDTO>>> listAllTickets() {
        List<Ticket> ticketList = ticketService.getAllTicketsByTime();
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketDTOList.add(TicketDTOMapper.mapTicketEntityToDTO(ticket));
        }
        final List<Resource<TicketDTO>> resourceTicketList = new ArrayList<>();
        for (TicketDTO t : ticketDTOList) {
            resourceTicketList.add(addResourceLinkToTicketDTO(t));
        }
        logger.info("Get all tickets." + Arrays.toString(ticketDTOList.toArray()));
        return new ResponseEntity<>(resourceTicketList, HttpStatus.OK);

    }


    private Resource<TicketDTO> addResourceLinkToTicketDTO(TicketDTO ticket) {
        Resource<TicketDTO> ticketResource = new Resource<>(ticket);
        ticketResource.add(linkTo(methodOn(TicketController.class)
                .getTicketById(ticket.getTicketId()))
                .withSelfRel());
        return ticketResource;
    }

    private Resource<Ticket> addResourceLinkToTicket(Ticket ticket) {
        Resource<Ticket> ticketResource = new Resource<>(ticket);
        ticketResource.add(linkTo(methodOn(TicketController.class)
                .getTicketById(ticket.getTicketId()))
                .withSelfRel());
        return ticketResource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Ticket>> getTicketById(@PathVariable("id") Integer ticketId) {
        Ticket ticket = ticketService.findOne(ticketId);
        if(ticket == null){
            logger.info("Get ticket by id: " + ticket + " no exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Get ticket by id: " + ticket);
        return new ResponseEntity<>(addResourceLinkToTicket(ticket), HttpStatus.OK);
    }


    @RequestMapping(value = "/map/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<TicketDTO>> getTicketByIdDTO(@PathVariable("id") Integer ticketId) {
        Ticket ticket = ticketService.findOne(ticketId);
        if(ticket == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketDTO ticketDTO = new TicketDTOMapper().mapTicketEntityToDTO(ticket);
        logger.info("Get ticketDTO by id: " + ticket);
        return new ResponseEntity<>(addResourceLinkToTicketDTO(ticketDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/state", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Ticket>> updateState(@RequestBody Ticket ticket) {

        logger.info("Updating ticketState: " + ticket.getTicketId());
        Ticket ticketDB = ticketService.findOne(ticket.getTicketId());
        ticketDB.setStateTime(new Timestamp(new Date().getTime()));
        ticketDB.setState(ticket.getState());

        ticket = ticketService.update(ticketDB);
        Settings settings = settingsService.findByUser(ticketDB.getUser());
        if (settings.getCreator()) {
            Notice notice = new Notice(ticket.getUser(), ticket.getName(), "home/user/ticket/" + ticket.getTicketId(), NoticeType.TO_CREATOR);
            noticeService.save(notice);
        }
        Resource<Ticket> ticketResource = new Resource<>(ticketDB);
        ticketResource.add(linkTo(methodOn(TicketController.class).getTicketById(ticket.getTicketId())).withSelfRel());
        return new ResponseEntity<>(ticketResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<Ticket>> updateTicket(@RequestBody Ticket ticket) {

        logger.info("Updating ticket by id: " + ticket);
        Ticket ticketDB = ticketService.findOne(ticket.getTicketId());
        ticket.setTime(ticketDB.getTime());

        User assigned = userService.findOne(ticket.getAssigned().getUserId());
        ticket.setAssigned(assigned);
        ticket.setStateTime(ticketDB.getStateTime());
        if (ticket.getState() != ticketDB.getState()) {
            Settings settings = settingsService.findByUser(ticketDB.getUser());
            if (settings.getCreator()) {
                Notice notice = new Notice(ticketDB.getUser(), ticket.getName(), "home/user/ticket/" + ticket.getTicketId(), NoticeType.TO_CREATOR);
                ticket.setStateTime(new Timestamp(new Date().getTime()));
                noticeService.save(notice);
            }
        }
        if (ticket.getAssigned() != ticketDB.getAssigned()) {
            Settings settings = settingsService.findByUser(ticket.getAssigned());
            if (settings.getAssigned()) {
                Notice notice = new Notice(ticket.getAssigned(), ticket.getName(), "home/user/ticket/" + ticket.getTicketId(), NoticeType.TO_ASSIGNED);
                noticeService.save(notice);
            }
        }
        ticket = ticketService.update(ticket);
        Resource<Ticket> ticketResource = new Resource<>(ticket);
        ticketResource.add(linkTo(methodOn(TicketController.class).getTicketById(ticket.getTicketId())).withSelfRel());
        return new ResponseEntity<>(ticketResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ticket> deleteTicketById(@PathVariable("id") Integer ticketId) {
        logger.info("removing ticket by id: " + ticketId);
        ticketService.delete(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Ticket> deleteAll() {
        logger.info("removing all tickets");
        ticketService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    public ResponseEntity<TicketPageDataObject> listTicketsByName(@RequestBody PageParams pageParams,
                                                                  @RequestParam(value = "name") String findName,
                                                                  @AuthenticationPrincipal Principal user ) {
        logger.info("get tickets by name: " + findName);
        final PageRequest pageRequest = new PageRequestGenerator(pageParams)
                .toPageRequest();
        User currentUser=userService.findUserByEmail(user.getName());
        Page<Ticket> ticketsByPage = ticketService.getTicketsByName(findName,currentUser, pageRequest);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(ticketsByPage);
        EntityResourceList<Ticket> ticketResourceLinkList = new TicketResourceList();
        ticketsByPage.forEach((ticket) -> ticketResourceLinkList.add(toResource(ticket)));
        TicketPageDataObject pageCreator = setUpPageCreator(pageSelector, ticketResourceLinkList);
        logger.info("tickets: " + ticketsByPage.toString());
        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public ResponseEntity<TicketPageDataObject> listTicketsByState(@RequestBody PageParams pageParams,
                                                                   @RequestParam(value = "state") TicketState ticketState,
                                                                   @AuthenticationPrincipal Principal user ) {
        logger.info("get tickets by state: " + ticketState);
        final PageRequest pageRequest = new PageRequestGenerator(pageParams)
                .toPageRequest();
        User currentUser=userService.findUserByEmail(user.getName());
        Page<Ticket> ticketsByPage = ticketService.getTicketsByState(currentUser,ticketState, pageRequest);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(ticketsByPage);
        EntityResourceList<Ticket> ticketResourceLinkList = new TicketResourceList();
        ticketsByPage.forEach((ticket) -> ticketResourceLinkList.add(toResource(ticket)));
        TicketPageDataObject pageCreator = setUpPageCreator(pageSelector, ticketResourceLinkList);
        logger.info("tickets: " + ticketsByPage.toString());
        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<TicketPageDataObject> listTicketsByUser(@RequestBody PageParams pageParams,
                                                                  @RequestParam(value = "user", required = false) String email,
                                                                  @RequestParam(value = "assign", required = false) String emailAssign,
                                                                  @RequestParam(value = "state", required = false) TicketState ticketState){
        logger.info("get tickets by user and state: " + email + "  " + emailAssign + "  " + ticketState);
        final PageRequest pageRequest = new PageRequestGenerator(pageParams)
                .toPageRequest();
        Page<Ticket> ticketsByPage;
        if (email != null) {
            User user = userService.findUserByEmail(email);
            ticketsByPage = ticketService.findTicketsByStateAndUser(ticketState, user, pageRequest);

        } else {
            User user = userService.findUserByEmail(emailAssign);
            ticketsByPage = ticketService.findTicketsByStateAndAssign(ticketState, user, pageRequest);

        }
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(ticketsByPage);
        EntityResourceList<Ticket> ticketResourceLinkList = new TicketResourceList();
        ticketsByPage.forEach((ticket) -> ticketResourceLinkList.add(toResource(ticket)));
        logger.info("get tickets by user and state: " + ticketsByPage.getTotalElements());

        TicketPageDataObject pageCreator = setUpPageCreator(pageSelector, ticketResourceLinkList);
        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<TicketPageDataObject> listAllTickets(@RequestBody PageParams pageParams,
                                                               @AuthenticationPrincipal Principal user) {
        logger.info("get all tickets by page number: " + pageParams.getPageNumber());
        final PageRequest pageRequest = new PageRequestGenerator(pageParams)
                .toPageRequest();
        User currentUser=userService.findUserByEmail(user.getName());
        logger.info("user current:" + currentUser);
        Page<Ticket> ticketsByPage = ticketService.findAllTickets(currentUser, pageRequest);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(ticketsByPage);
        EntityResourceList<Ticket> ticketResourceLinkList = new TicketResourceList();
        ticketsByPage.forEach((ticket) -> ticketResourceLinkList.add(toResource(ticket)));
        TicketPageDataObject pageCreator = setUpPageCreator(pageSelector, ticketResourceLinkList);
        return new ResponseEntity<>(pageCreator, HttpStatus.OK);
    }

    private TicketPageDataObject setUpPageCreator(PageRequestGenerator.PageSelector pageSelector, List<Resource<Ticket>> resourceList) {
        TicketPageDataObject pageCreator = new TicketPageDataObject();
        pageCreator.setRows(resourceList);
        pageCreator.setCurrentPage(Integer.valueOf(pageSelector.getCurrentPage()).toString());
        pageCreator.setBeginPage(Integer.valueOf(pageSelector.getBegin()).toString());
        pageCreator.setEndPage(Integer.valueOf(pageSelector.getEnd()).toString());
        pageCreator.setTotalPages(Integer.valueOf(pageSelector.getTotalPages()).toString());
        return pageCreator;
    }

}