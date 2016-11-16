/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.paging.impl;

import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.util.paging.PageDataObject;
import org.springframework.hateoas.Resource;

import java.util.List;

/**
 * Created by Kris on 29.08.2016.
 */
public class TicketPageDataObject extends PageDataObject<Resource<Ticket>> {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

}
