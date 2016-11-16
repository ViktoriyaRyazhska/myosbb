/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.controller.TicketController;
import com.softserve.osbb.model.Ticket;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Kris on 23.08.2016.
 */
@SuppressWarnings("serial")
public class TicketResourceList extends EntityResourceList<Ticket> {

    @Override
    public Resource<Ticket> createLink(Resource<Ticket> reportResource) {
        Ticket report = reportResource.getContent();

        reportResource.add(linkTo(methodOn(TicketController.class)
                .getTicketById(report.getTicketId())).withSelfRel());

        return reportResource;
    }
}
