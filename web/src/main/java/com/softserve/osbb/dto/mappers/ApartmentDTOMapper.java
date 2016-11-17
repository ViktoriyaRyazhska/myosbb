/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import org.springframework.stereotype.Component;

import com.softserve.osbb.dto.ApartmentDTO;
import com.softserve.osbb.model.Apartment;

/**
 * Created by Oleg on 20.08.2016.
 */
@Component
public class ApartmentDTOMapper {
    
    public static ApartmentDTO mapApartmentEntityToDTO(Apartment apartment) {
        ApartmentDTO apartmentDTO = new ApartmentDTO();
        
        if (apartment != null) {
            apartmentDTO.setApartmentId(apartment.getApartmentId());
            apartmentDTO.setNumber(apartment.getNumber());
            apartmentDTO.setSquare(apartment.getSquare());
            if (apartment.getOwner() != null) {
                apartmentDTO.setOwner(apartment.getOwner());

            }
            apartmentDTO.setHouse(apartment.getHouse());
        }
        
        return apartmentDTO;
    }
    
}