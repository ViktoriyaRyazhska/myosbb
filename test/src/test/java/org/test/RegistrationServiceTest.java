package org.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.Registration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.RegistrationService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.RegistrationServiceImpl;

public class RegistrationServiceTest {

	@Mock
	private RegistrationService registrationService;
	
	@Mock
	private Registration registration;
	
	@Mock
	User user;
	
	@Mock
	Osbb osbbObj;
	
	@InjectMocks
	private RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void registrateTest() {
		when(registrationServiceImpl.registrate(user)).thenReturn(user);
		when(registrationServiceImpl.registrate(osbbObj)).thenReturn(osbbObj);
		registrationServiceImpl.registrate(user);
		registrationServiceImpl.registrate(osbbObj);
	}
	
	@Test
	public void getPasswordTest() {
	     registrationServiceImpl. getPassword();
	}
	
	@Test
	public void setPasswordTest() {
		registrationServiceImpl.setPassword("name");
	}
}
