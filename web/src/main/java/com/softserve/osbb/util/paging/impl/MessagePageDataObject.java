/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */package com.softserve.osbb.util.paging.impl;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.util.paging.PageDataObject;

/**
 * Created by Kris on 29.08.2016.
 */
public class MessagePageDataObject extends PageDataObject<Resource<MessageDTO>> {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }
}
