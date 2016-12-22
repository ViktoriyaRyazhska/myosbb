/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.HouseDTO;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;

import java.util.List;

/**
 * Created by nazar.dovhyy on 03.08.2016.
 */
public class HouseDTOMapper {

    public static HouseDTO mapHouseEntityToDTO(House house) {
        HouseDTO houseDTO = null;
        
        if (house != null) {
            houseDTO = new HouseDTO.HouseDTOBuilder()
                    .setHouseId(house.getHouseId())
               		.setNumberHouse(house.getNumberHouse())
                    .setZipCode(house.getZipCode())
                    .setStreet(house.getStreet().getId())
                    .setOsbb(house.getOsbb())
                    .build();
        }
        return houseDTO;
    }


    public static House mapHouseDTOToHouse(HouseDTO houseDTO) {
        House house = null;
        
        if (houseDTO != null) {
            if (houseDTO.getHouseId() != null) {
                //// TODO: 28.08.2016  update existing house
            }

            house = new House();
            mapFromHouseDTOToHouse(houseDTO, house);
        }

        return house;
    }

    private static void mapFromHouseDTOToHouse(HouseDTO houseDTO, House house) {
        house.setZipCode(houseDTO.getZipCode());
        house.setDescription(houseDTO.getDescription());
    }
    
}
