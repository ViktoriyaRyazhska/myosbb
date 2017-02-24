package com.softserve.osbb.service.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.softserve.osbb.service.exceptions.InvalidEmailException;

@Component
public class EmailValidator {
	
	/** simple e-mail validation reg-ex */
	private final String EMAIL_VALIDATION_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	public void validateEmail(String address) throws UnknownHostException {
		if(!address.matches(EMAIL_VALIDATION_REGEX)){
			throw new InvalidEmailException("E-mail isn't valid");
		}
		int pos = address.indexOf( '@' );
	     // Isolate the domain/machine name and get a list of mail exchangers
	     String domain = address.substring( ++pos );
	     InetAddress.getByName(domain);
		
	}
	

	
}
