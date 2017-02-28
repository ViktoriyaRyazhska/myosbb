package com.softserve.osbb.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.UnknownHostException;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nazar Kohut
 */

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.ApartmentRepository;
import com.softserve.osbb.repository.SettingsRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.impl.AppartmentUserRegistrationServiceImpl;
import com.softserve.osbb.service.impl.MailSenderImpl;

public class AppartmentUserRegistrationServiceTest {

	@InjectMocks
	private AppartmentUserRegistrationServiceImpl appartmentUserRegistrationServiceImpl;

	@Mock
	private ApartmentRepository apartmentRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SettingsRepository settingsRepository;

	@Mock
	private MailSenderImpl mailSenderImpl;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private Apartment apartment;

	@Mock
	private House house;

	@Mock
	private User user;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void registerAppartmentWithUserTest() throws MessagingException {

		when(apartmentRepository.save(apartment)).thenReturn(apartment);
		when(userRepository.save(user)).thenReturn(user);
		Apartment ap1 = null;
		Apartment ap2 = null;
		try {
			ap1 = appartmentUserRegistrationServiceImpl.registerAppartmentWithUser(user, apartment, house);
			ap2 = appartmentUserRegistrationServiceImpl.registerAppartmentWithUser(user, apartment, house);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(apartmentRepository, times(2)).saveAndFlush(apartment);
		
		assertEquals(apartment, ap1);
		assertEquals(apartment, ap2);
	}

}
