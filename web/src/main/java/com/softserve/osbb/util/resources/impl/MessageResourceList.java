package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.controller.MessageController;
import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.model.Message;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Kris on 23.08.2016.
 */
public class MessageResourceList extends EntityResourceList<MessageDTO> {
    @Override
    public Resource<MessageDTO> createLink(Resource<MessageDTO> reportResource) {
        MessageDTO report = reportResource.getContent();

        reportResource.add(linkTo(methodOn(MessageController.class)
                .getMessageById(report.getMessageId())).withSelfRel());


        return reportResource;
    }
}
