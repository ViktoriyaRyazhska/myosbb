/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.controller.BillController;
import com.softserve.osbb.dto.BillDTO;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nazar.dovhyy on 18.08.2016.
 */
@SuppressWarnings("serial")
public class BillResourceList extends EntityResourceList<BillDTO> {
    @Override
    public Resource<BillDTO> createLink(Resource<BillDTO> billResource) {
        BillDTO billObject = billResource.getContent();
        billResource.add(linkTo(methodOn(BillController.class).findOneBill(billObject.getBillId()))
                .withSelfRel());
        return billResource;
    }
}
