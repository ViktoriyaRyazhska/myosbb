package com.softserve.osbb.dto.mappers;


import com.softserve.osbb.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Created by ndovhuy on 27.10.2016.
 */
public abstract class AbstractDTOMapper<T extends Serializable, K extends AbstractDTO> {


    /**
     * @param entity
     * @return
     */
    public abstract K mapEntityToDTO(T entity);


    /**
     * @param dto
     * @return
     */
    public abstract T mapDTOToEntity(K dto);


}
