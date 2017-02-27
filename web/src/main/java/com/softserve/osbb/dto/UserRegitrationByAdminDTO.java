package com.softserve.osbb.dto;

import java.io.Serializable;

import com.softserve.osbb.model.enums.Ownership;


public class UserRegitrationByAdminDTO extends AbstractDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Ownership ownership;
	
	public UserRegitrationByAdminDTO() {
	}

	public UserRegitrationByAdminDTO(String firstName, String lastName, String email, String phoneNumber,
			Ownership ownership) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.ownership = ownership;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Ownership getOwnership() {
		return ownership;
	}

	public void setOwnership(Ownership owneship) {
		this.ownership = owneship;
	}

	@Override
	public String toString() {
		return "UserRegitrationByAdminDTO {firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", owneship=" + ownership.name() + "}";
	}
	
	
	
	
}
