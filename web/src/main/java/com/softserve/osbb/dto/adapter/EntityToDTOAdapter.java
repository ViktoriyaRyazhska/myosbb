/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.adapter;

import com.softserve.osbb.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Created by ndovhuy on 27.10.2016.
 */
public abstract class EntityToDTOAdapter<T extends Serializable, K extends AbstractDTO> {

    protected Serializable entity;

    public EntityToDTOAdapter(T entity) {
        this.entity = entity;
    }

    public abstract K parse();
}
