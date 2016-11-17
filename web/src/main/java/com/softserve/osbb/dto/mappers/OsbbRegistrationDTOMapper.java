/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.OsbbRegistrationDTO;
import com.softserve.osbb.dto.adapter.OsbbRegistartionToOsbbAdapter;
import com.softserve.osbb.model.Osbb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nazar.dovhyy on 29.10.2016.
 */
@Component
public class OsbbRegistrationDTOMapper extends AbstractDTOMapper<Osbb, OsbbRegistrationDTO> {

    @Autowired
    private OsbbRegistartionToOsbbAdapter osbbRegistartionToOsbbAdapter;

    @Override
    public OsbbRegistrationDTO mapEntityToDTO(Osbb entity) {
        return null;
    }

    @Override
    public Osbb mapDTOToEntity(OsbbRegistrationDTO dto) {
        return osbbRegistartionToOsbbAdapter.parse(dto);
    }
}
