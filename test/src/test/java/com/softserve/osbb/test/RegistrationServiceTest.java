package com.softserve.osbb.test;


import static org.mockito.Mockito.when;


import javax.servlet.Registration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.RegistrationService;
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
	
	
}
