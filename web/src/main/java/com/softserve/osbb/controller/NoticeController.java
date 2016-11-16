/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Notice;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.NoticeService;
import com.softserve.osbb.service.UserService;

/**
 * Created by Kris on 06.09.2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/notice")
public class NoticeController {

    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Notice>> createNotice(@RequestBody Notice notice) {
        Resource<Notice> noticeResource;
        
        try {
            notice.setUser(userService.findOne(notice.getUser().getUserId()));
            notice = noticeService.save(notice);
            noticeResource = addResourceLinkToNotice(notice);
            logger.info("Saving notice object " + notice.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(noticeResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Notice>>> getNoticeByUser(
            @AuthenticationPrincipal Principal user) {
        User currentUser = userService.findUserByEmail(user.getName());
        List<Notice> noticeList = noticeService.findNoticesOfUser(currentUser);
        List<Resource<Notice>> resourceNoticeList = new ArrayList<>();
        
        for (Notice e : noticeList) {
            Resource<Notice> resourceNotice = new Resource<>(e);
            resourceNotice.add(linkTo(methodOn(NoticeController.class)
                    .getNoticeById(e.getNoticeId()))
                    .withSelfRel());
            resourceNoticeList.add(resourceNotice);
        }

        return new ResponseEntity<>(resourceNoticeList, HttpStatus.OK);
    }

    private Resource<Notice> addResourceLinkToNotice(Notice notice) {
        Resource<Notice> noticeResource = new Resource<>(notice);

        noticeResource.add(linkTo(methodOn(NoticeController.class)
                .getNoticeById(notice.getNoticeId()))
                .withSelfRel());
        return noticeResource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Notice>> getNoticeById(@PathVariable("id") Integer noticeId) {
        logger.info("Fetching notice by id: " + noticeId);
        Resource<Notice> noticeResource = addResourceLinkToNotice(noticeService.findOne(noticeId));
        noticeResource.add(linkTo(methodOn(NoticeController.class).getNoticeById(noticeId)).withSelfRel());
        return new ResponseEntity<>(noticeResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Notice> deleteNoticeById(@PathVariable("id") Integer noticeId) {
        logger.info("removing notice by id: " + noticeId);
        noticeService.delete(noticeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
