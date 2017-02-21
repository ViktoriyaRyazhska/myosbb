package org.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.SettingsRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.SettingsService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.SettingsServiceImpl;

public class SettingsServiceTest {

	@Mock
	private SettingsService settingsService;
	
	@Mock
	private Settings settings;
	
	@Mock
	User user;
	
	@Mock
	SettingsRepository settingsRep;
	
	@InjectMocks
	private SettingsServiceImpl settingsServiceImpl = new SettingsServiceImpl();


	@Before
	public void setUp() throws Exception {	
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveTest() {
		when(settingsServiceImpl.save(settings)).thenReturn(settings);
		settingsServiceImpl.save(settings);
		verify(settingsRep, times(1)).save(settings);
	}
	
	@Test
	public void updateTest(){
		settingsServiceImpl.update(settings);
    }
	
	@Test
	public void findOneTest() {
		when(settingsServiceImpl.findOne(1)).thenReturn(settings);
		settingsServiceImpl.findOne(1);
		verify(settingsRep, times(1)).findOne(1);
	}
	
	@Test
	public void findByUserTest() {
		when(settingsServiceImpl.findByUser(user)).thenReturn(settings);
		settingsServiceImpl.findByUser(user);
		verify(settingsRep, times(1)).findByUser(user);
	}
}
