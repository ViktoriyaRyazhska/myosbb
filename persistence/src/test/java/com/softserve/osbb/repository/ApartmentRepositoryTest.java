package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceConfiguration;
import com.softserve.osbb.model.Apartment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Oleg on 05.07.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceConfiguration.class)
@Rollback
@Transactional
public class ApartmentRepositoryTest {
public static final Integer APPNUMBER = 111;
private Apartment apartment;
    @Autowired
    ApartmentRepository apartmentRepository;

    @Before
    public void init(){
        apartment = new Apartment();
        this.apartment.setNumber(APPNUMBER);
        this.apartment.setSquare(111);
    }

    @Test
    public void testSave(){

        apartment= apartmentRepository.save(apartment);
        Assert.assertNotNull(apartment);
        Assert.assertEquals(APPNUMBER, apartmentRepository.findOne(apartment.getApartmentId()).getNumber() );
    }

    @Test
    public void testDelete(){
        apartment= apartmentRepository.save(apartment);
        apartmentRepository.delete(apartment.getApartmentId());
        Assert.assertNull(apartmentRepository.findOne(apartment.getApartmentId()));
    }

}
