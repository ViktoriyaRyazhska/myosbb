/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.controller.HouseController;
import com.softserve.osbb.dto.HouseDTO;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
@SuppressWarnings("serial")
public class HouseResourceList extends EntityResourceList<HouseDTO> {

    @Override
    public Resource<HouseDTO> createLink(Resource<HouseDTO> houseResource) {
        HouseDTO house = houseResource.getContent();

        houseResource.add(linkTo(methodOn(HouseController.class)
                .getHouseById(house.getHouseId()))
                .withSelfRel());

        return houseResource;
    }
}
