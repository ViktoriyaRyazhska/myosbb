package com.softserve.osbb.util.paging.impl;

import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.util.paging.PageDataObject;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
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

    @Override
    public PageDataObject<Resource<Ticket>> providePageData(PageRequestGenerator.PageSelector pageSelector, List<Resource<Ticket>> billResourceList) {
        throw new UnsupportedOperationException();
    }
}
