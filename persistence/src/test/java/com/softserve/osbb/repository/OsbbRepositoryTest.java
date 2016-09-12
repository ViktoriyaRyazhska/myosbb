package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceConfiguration;
import com.softserve.osbb.model.Osbb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Roman on 06.07.2016.
 */
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceConfiguration.class)
@Transactional
public class OsbbRepositoryTest {

    private Osbb osbb;

    public static final String OSBB_NAME = "МоєОСББ";

    @Autowired
    OsbbRepository osbbRepository;

    @Before
    public void init() {
        osbb = new Osbb();
        osbb.setName(OSBB_NAME);
        osbb.setDescription("осбб для людей");
        osbb.setDistrict("Залізничний");
        osbb.setAddress("м.Львів вул.Городоцька 165а");
    }

    @Test
    public void testSave(){
        Osbb savedOsbb = osbbRepository.save(osbb);
        assertEquals(savedOsbb, osbbRepository.findOne(osbb.getOsbbId()));
    }

    @Test
    public void testFindByName() {
        osbbRepository.save(osbb);
        Osbb foundedOsbb = osbbRepository.findByName(OSBB_NAME);
        assertNotNull(foundedOsbb);
        assertEquals(OSBB_NAME, foundedOsbb.getName());
    }

    @Test
    public void testGetOsbbById() {
        Osbb savedOsbb = osbbRepository.save(osbb);
        assertEquals(osbbRepository.getOne(savedOsbb.getOsbbId()),savedOsbb);
    }

    @Test
    public void testGetAllOsbb() {
        List<Osbb> list = Arrays.asList(new Osbb(), new Osbb(), new Osbb());
        osbbRepository.deleteAll();
        osbbRepository.save(list);
        assertTrue(list.size() == osbbRepository.findAll().size());
    }

    @Test
    public void testDeleteOsbbById() {
        Osbb osbb = osbbRepository.save(this.osbb);
        osbbRepository.delete(osbb.getOsbbId());
        assertFalse(osbbRepository.exists(osbb.getOsbbId()));
    }

    @Test
    public void testDeleteOsbbByOsbb() {
        osbbRepository.save(osbb);
        osbbRepository.delete(osbb);
        assertFalse(osbbRepository.exists(osbb.getOsbbId()));
    }

    @Test
    public void testDeleteAllOsbb() {
        Assert.assertNotEquals(0, osbbRepository.findAll().size());
        osbbRepository.deleteAll();
        assertEquals(0, osbbRepository.findAll().size());
    }

}