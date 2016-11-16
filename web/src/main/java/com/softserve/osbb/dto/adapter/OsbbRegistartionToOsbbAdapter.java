/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.adapter;

import com.softserve.osbb.dto.OsbbRegistrationDTO;
import com.softserve.osbb.model.Osbb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nazar.dovhyy on 29.10.2016.
 */
@Component
public class OsbbRegistartionToOsbbAdapter extends DTOToEntityAdapter<OsbbRegistrationDTO, Osbb> {

    @Autowired
    private UserRegistrationToUserAdapter userRegistrationToUserAdapter;

    @Override
    public Osbb parse(OsbbRegistrationDTO osbbRegistrationDTO) {
        Osbb registeredOsbb = new Osbb();
        
        if (osbbRegistrationDTO == null) {
            if (dto == null) {
                throw new IllegalArgumentException();
            }
            
            osbbRegistrationDTO = (OsbbRegistrationDTO) dto;
            _parse(osbbRegistrationDTO, registeredOsbb);
        } else {
            _parse(osbbRegistrationDTO, registeredOsbb);
        }
        
        return registeredOsbb;
    }

    private void _parse(OsbbRegistrationDTO osbbRegistrationDTO, Osbb registeredOsbb) {
        registeredOsbb.setName(osbbRegistrationDTO.getName());
        registeredOsbb.setDescription(osbbRegistrationDTO.getDescription());
        registeredOsbb.setCreator(userRegistrationToUserAdapter.parse(osbbRegistrationDTO.getCreator()));
        registeredOsbb.setAddress(osbbRegistrationDTO.getAddress());
        registeredOsbb.setDistrict(osbbRegistrationDTO.getDistrict());
        registeredOsbb.setCreationDate(osbbRegistrationDTO.getCreationDate());
        registeredOsbb.setAvailable(registeredOsbb.getAvailable());

    }
}
