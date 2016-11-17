/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.util.resources.ResourceLinkCreator;
import org.springframework.hateoas.Resource;

import java.util.ArrayList;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
@SuppressWarnings("serial")
public abstract class EntityResourceList<T> extends ArrayList<Resource<T>> implements ResourceLinkCreator<T> {

    @Override
    public boolean add(Resource<T> tResource) {
        return super.add(createLink(tResource));
    }
}
