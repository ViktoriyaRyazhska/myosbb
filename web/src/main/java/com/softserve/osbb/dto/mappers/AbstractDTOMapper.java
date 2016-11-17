/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Created by ndovhuy on 27.10.2016.
 * @version 1.1
 * @since 16.11.2016
 */
public abstract class AbstractDTOMapper<T extends Serializable, K extends AbstractDTO> {

    /**
     * @param entity entity to be mapped
     * @return mapped entity
     */
    public abstract K mapEntityToDTO(T entity);

    /**
     * @param dto entity to be mapped
     * @return mapped entity
     */
    public abstract T mapDTOToEntity(K dto);

}
