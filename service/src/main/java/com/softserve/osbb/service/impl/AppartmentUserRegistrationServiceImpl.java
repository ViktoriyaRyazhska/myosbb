package com.softserve.osbb.service.impl;

import java.net.UnknownHostException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.enums.Ownership;
import com.softserve.osbb.repository.ApartmentRepository;
import com.softserve.osbb.repository.SettingsRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.AppartmentUserRegistrationService;
import com.softserve.osbb.service.utils.EmailValidator;

@Service
public class AppartmentUserRegistrationServiceImpl implements AppartmentUserRegistrationService {
	@Autowired
	ApartmentRepository apartmentRepository;

	@Autowired
	private MailSenderImpl sender;

	@Autowired
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	UserRepository userRepository;

	@Autowired
	SettingsRepository settingsRepository;
	
	@Autowired
	EmailValidator emailValidator;

	@Override
	@Transactional(readOnly = false,rollbackFor = UnknownHostException.class, propagation = Propagation.REQUIRED)
	public Apartment registerAppartmentWithUser(User user, Apartment apartment, House house) throws MessagingException, UnknownHostException {
		apartment.setHouse(house);
		apartment = apartmentRepository.save(apartment);

		user.setApartment(apartment);
		user.setHouse(house);
		user.setOsbb(house.getOsbb());
		String email = user.getEmail();
		emailValidator.validateEmail(email);
		sender.send(email, "Registation", user.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);

		settingsRepository.save(new Settings(user));
		// enum or const
		if (user.getOwnership() == Ownership.OWNER) {
			apartment.setOwner(user.getUserId());
		}
		apartment =	apartmentRepository.saveAndFlush(apartment);
		return apartment;
	}

}
