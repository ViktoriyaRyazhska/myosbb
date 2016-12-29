package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceConfiguration;
import com.softserve.osbb.model.House;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceConfiguration.class)
@Transactional
public class HouseRepositoryTest {

    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ApartmentRepository apartmentRepository;

    private House house;


    @Test
    public void testDeleteHouseThatExists(){

        House savedHouse = houseRepository.save(house);
        assertTrue(houseRepository.exists(savedHouse.getHouseId()));
        houseRepository.delete(savedHouse);
        assertFalse(houseRepository.exists(house.getHouseId()));

    }


    @Test
    public void testGetAllAppartmentsByHouseid(){

        /*
        Integer houseId = Integer.valueOf(9);
        House house = houseRepository.findOne(houseId);
        System.out.println(house.getApartments());
        */

       // houseRepository.delete(9);

    }

}