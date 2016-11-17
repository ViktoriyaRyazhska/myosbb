/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import com.softserve.osbb.model.Event;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.enums.EventStatus;
import com.softserve.osbb.service.EventService;
import com.softserve.osbb.util.paging.PageDataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nataliia on 10.07.16.
 */
@CrossOrigin
@RestController
@RequestMapping("/restful/event")
public class EventController {

    private static Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Event>>> findAllEvents() {
        logger.info("Getting all events.");
        List<Event> eventList = new ArrayList<Event>();
        eventList.addAll(eventService.getAllEvents());        
        List<Resource<Event>> resourceEventList = new ArrayList<>();
        eventList.forEach((event) -> resourceEventList.add(getResourceWithLink(toResource(event))));        
        return new ResponseEntity<>(resourceEventList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "",  method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteAllEvents() {
        logger.info("Removing all events.");
        eventService.deleteAllEvents();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Event>> findEventById(@PathVariable("id") Integer eventId) {
        logger.info("Getting event by id: " + eventId);
        Event event = eventService.getEventById(eventId);
        Resource<Event> eventResource = new Resource<>(event);
        eventResource = getResourceWithLink(eventResource);
        logger.info("Event user : " + eventResource.getContent().getAuthor());
        return new ResponseEntity<>(eventResource, HttpStatus.OK);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Resource<Event>> createEvent(@RequestBody Event event) {
        logger.info("Saving event " + event);
        event = eventService.saveEvent(event);
        Resource<Event> eventResource = new Resource<>(event);
        eventResource = getResourceWithLink(eventResource);
        return new ResponseEntity<>(eventResource, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Event>> updateEvent(@RequestBody Event event) {
        logger.info("Updating event by id: " + event.getEventId());
        event = eventService.updateEvent(event.getEventId(), event);
        Resource<Event> eventResource = new Resource<>(event);
        eventResource = getResourceWithLink(eventResource);
        return new ResponseEntity<>(eventResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEventById(@PathVariable("id") Integer eventId) {
        logger.info("Removing event by id: " + eventId);
        eventService.deleteEventById(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Event>>> listAllEvents(
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder) {
        logger.info("get all event by page number: " + pageNumber);
        Page<Event> eventsByPage = eventService.getAllEvents(pageNumber, sortedBy, ascOrder);

        Integer currentPage = eventsByPage.getNumber() + 1;
        Integer begin = Math.max(1, currentPage - 5);
        Integer totalPages = eventsByPage.getTotalPages();
        Integer end = Math.min(currentPage + 5, totalPages);

        List<Resource<Event>> resourceList = new ArrayList<>();
        eventsByPage.forEach((event) -> resourceList.add(getResourceWithLink(toResource(event))));

        PageDataObject<Resource<Event>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(currentPage.toString());
        pageDataObject.setBeginPage(begin.toString());
        pageDataObject.setEndPage(end.toString());
        pageDataObject.setTotalPages(totalPages.toString());

        return new ResponseEntity<>(pageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Event>>> getByInterval(
            @RequestParam(value = "start") Long start,
            @RequestParam(value = "end") Long end) {
        List<Event> events = eventService.findByInterval(new Timestamp(start), new Timestamp(end));

        List<Resource<Event>> resourceEventList = new ArrayList<>();
        events.forEach((event) -> resourceEventList.add(getResourceWithLink(toResource(event))));

        return new ResponseEntity<>(resourceEventList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Event>>> getEventsByTitle(
            @RequestParam(value = "title") String title) {
        logger.info("fetching event by search parameter: " + title);
        List<Event> eventsBySearchTerm = eventService.findEventsByTitleOrAuthorOrDescription(title);
        
        if (eventsBySearchTerm.isEmpty()) {
            logger.warn("no events were found");
        }
        
        List<Resource<Event>> resourceEventList = new ArrayList<>();
        eventsBySearchTerm.forEach((event) -> resourceEventList.add(getResourceWithLink(toResource(event))));
        return new ResponseEntity<>(resourceEventList, HttpStatus.OK);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Event>>> getEventsByStatus(
            @RequestParam(value = "status") EventStatus status) {
        logger.info("fetching event by status: " + status);
        List<Event> eventsBySearchTerm = eventService.findByStatus(status);
        
        if (eventsBySearchTerm.isEmpty()) {
            logger.warn("no events were found");
        }
        
        List<Resource<Event>> resourceEventList = new ArrayList<>();
        eventsBySearchTerm.forEach((event) -> resourceEventList.add(getResourceWithLink(toResource(event))));
        return new ResponseEntity<>(resourceEventList, HttpStatus.OK);
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Event>>> getEventsByUser(
            @AuthenticationPrincipal Principal principal) {
        logger.info("fetching event by author: " + principal.getName());
        System.out.println("FIND BY AUTHOR " + principal.getName());
        List<Event> eventsBySearchTerm = eventService.findByAuthor(principal.getName());
        
        if (eventsBySearchTerm.isEmpty()) {
            logger.warn("no events were found");
        }
        
        List<Resource<Event>> resourceEventList = new ArrayList<>();
        eventsBySearchTerm.forEach((event) -> resourceEventList.add(getResourceWithLink(toResource(event))));
        return new ResponseEntity<>(resourceEventList, HttpStatus.OK);
    }

    private Resource<Event> getResourceWithLink(Resource<Event> eventResource) {
        //adding self-link
        eventResource.add(linkTo(methodOn(EventController.class)
                .findEventById(eventResource.getContent().getEventId()))
                .withSelfRel());
        
        //adding link to osbb
        final Osbb osbbFromResource = eventResource.getContent().getOsbb();
        
        if (osbbFromResource != null) {
            eventResource.add(linkTo(methodOn(OsbbController.class)
                    .getOsbbById(osbbFromResource
                            .getOsbbId())).withRel("osbb"));
        }
        
        return eventResource;
    }
}
