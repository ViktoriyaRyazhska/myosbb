/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.OsbbDTO;
import com.softserve.osbb.model.Osbb;

/**
 * Created by Roman on 22.09.2016.
 */
public class OsbbDTOMapper {
    public static OsbbDTO mapOsbbEntityToDTO(Osbb osbb) {
        OsbbDTO osbbDTO = new OsbbDTO();
        
        if(osbb != null) {
            osbbDTO.setOsbbId(osbb.getOsbbId());
            osbbDTO.setName(osbb.getName());
            osbbDTO.setDescription(osbb.getDescription());
            osbbDTO.setAddress(osbb.getAddress());
            osbbDTO.setDistrict(osbb.getDistrict());
            osbbDTO.setLogo(osbb.getLogo());
            osbbDTO.setCreationDate(osbb.getCreationDate());
            osbbDTO.setCreator(osbb.getCreator());
            osbbDTO.setCountOfHouses(osbb.getHouses().size());
            osbbDTO.setCountOfUsers(osbb.getUsers().size());
        }
        return osbbDTO;
    }
    
}
