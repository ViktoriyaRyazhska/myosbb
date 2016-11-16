/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.softserve.osbb.controller.MessageController;
import com.softserve.osbb.dto.MessageDTO;

/**
 * Created by Kris on 23.08.2016.
 */
@SuppressWarnings("serial")
public class MessageResourceList extends EntityResourceList<MessageDTO> {
    
    @Override
    public Resource<MessageDTO> createLink(Resource<MessageDTO> reportResource) {
        MessageDTO report = reportResource.getContent();

        reportResource.add(linkTo(methodOn(MessageController.class)
                .getMessageById(report.getMessageId())).withSelfRel());

        return reportResource;
    }
}
