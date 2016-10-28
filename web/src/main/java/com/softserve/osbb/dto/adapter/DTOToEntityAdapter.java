package com.softserve.osbb.dto.adapter;

import com.softserve.osbb.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Created by ndovhuy on 27.10.2016.
 */
public abstract class DTOToEntityAdapter<T extends AbstractDTO, K extends Serializable> {

    protected AbstractDTO dto;
    /**
     * call this method before calling parse
     * @param dto
     */
    public void setDto(AbstractDTO dto) {
        this.dto = dto;
    }

    /**
     * pass null if you have already set dto
     * or dto object that you intend to parse
     * @param dto
     * @return
     */
    public abstract K parse(T dto);

}
