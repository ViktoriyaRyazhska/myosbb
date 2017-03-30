package com.softserve.osbb.test;

import com.softserve.osbb.model.Utility;
import com.softserve.osbb.repository.UtilityRepository;
import com.softserve.osbb.service.UtilityService;
import com.softserve.osbb.service.impl.UtilityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class UtilityServiceTest {

    @Mock
    private UtilityService utilityService;

    @Mock
    private Utility utility;

    @Mock
    private List<Utility> list;

    @Mock
    private UtilityRepository utilityRepository;

    @InjectMocks
    UtilityServiceImpl utilityServiceImpl = new UtilityServiceImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void saveTest() {

        when(utilityServiceImpl.save(utility)).thenReturn(utility);

        utilityServiceImpl.save(utility);

        verify(utilityRepository, times(1)).saveAndFlush(utility);
    }

    @Test
    public void findAllTest() {
        when(utilityServiceImpl.getAll()).thenReturn(list);

        utilityServiceImpl.getAll();

        verify(utilityRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() {
        when(utilityServiceImpl.findById(1)).thenReturn(utility);
        when(utilityServiceImpl.findById(2)).thenReturn(utility);

        utilityServiceImpl.findById(1);
        utilityServiceImpl.findById(2);

        verify(utilityRepository, times(1)).findOne(1);
        verify(utilityRepository, times(1)).findOne(2);
    }

    @Test
    public void deleteTest() {
        doNothing().when(utilityService).delete(1);
        doNothing().when(utilityService).delete(2);

        utilityServiceImpl.delete(1);
        utilityServiceImpl.delete(2);

        verify(utilityRepository, times(1)).delete(1);
        verify(utilityRepository, times(1)).delete(2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateTest() {

        Utility utility1 = new Utility();
        Utility utility2 = new Utility();

        utility1.setUtilityId(1);
        utility2.setUtilityId(2);

        utilityServiceImpl.save(utility1);

        when(utilityRepository.exists(utility1.getUtilityId())).thenReturn(true);
        when(utilityRepository.exists(utility2.getUtilityId())).thenReturn(false);
        when(utilityServiceImpl.updateUtility(utility1)).thenReturn(utility1);

        utilityServiceImpl.updateUtility(utility1);
        utilityServiceImpl.updateUtility(utility2);
        when(utilityServiceImpl.updateUtility(null));

        verify(utilityRepository, times(1)).saveAndFlush(utility1);

    }
}
