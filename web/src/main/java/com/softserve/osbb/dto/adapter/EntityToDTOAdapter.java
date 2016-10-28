package com.softserve.osbb.dto.adapter;

import com.softserve.osbb.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Created by ndovhuy on 27.10.2016.
 */
public abstract class EntityToDTOAdapter<T extends Serializable, K extends AbstractDTO> {

    protected Serializable entity;


    public <T extends Serializable> EntityToDTOAdapter(T entity) {
        this.entity = entity;
    }

    public abstract K parse();
}
