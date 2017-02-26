package com.softserve.osbb.service;

import java.net.UnknownHostException;

import javax.mail.MessagingException;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;

public interface AppartmentUserRegistrationService {


	 public Apartment registerAppartmentWithUser(User user, Apartment apartment, House house)
			throws MessagingException, UnknownHostException;
}
