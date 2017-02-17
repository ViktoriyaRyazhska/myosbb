package com.softserve.osbb.dto;

import java.io.Serializable;

import com.softserve.osbb.model.Apartment;

public class AppartmentUserDTO  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Apartment apartment;
	private UserRegitrationByAdminDTO userRegitrationByAdminDTO;

	public AppartmentUserDTO() {
		super();
	}

	public AppartmentUserDTO(AppartmentUserDTOBuilder builder) {
		this.apartment = builder.apartment;
		this.userRegitrationByAdminDTO = builder.user;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public UserRegitrationByAdminDTO getUserRegitrationByAdminDTO() {
		return userRegitrationByAdminDTO;
	}

	public void setUserRegitrationByAdminDTO(UserRegitrationByAdminDTO user) {
		this.userRegitrationByAdminDTO = user;
	}

	public static class AppartmentUserDTOBuilder {
		private Apartment apartment;
		private UserRegitrationByAdminDTO user;

		public AppartmentUserDTOBuilder setApartment(Apartment apartment) {
			this.apartment = apartment;
			return this;
		}

		public AppartmentUserDTOBuilder setUser(UserRegitrationByAdminDTO user) {
			this.user = user;
			return this;
		}

		public AppartmentUserDTO build() {
			return new AppartmentUserDTO(this);
		}
	}

}
