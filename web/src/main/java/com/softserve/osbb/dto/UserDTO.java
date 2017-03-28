/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto;

import com.softserve.osbb.dto.mappers.ApartmentDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;

import java.util.Date;

/**
 * Created by Roman on 16.08.2016.
 * modified by cavayman 23.09.2016
 * modified by YuriiRozhak 12.03.2017
 */
public class UserDTO {
    
    private Integer userId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private Integer osbbId;
    private String gender;
    private String password;
    private ApartmentDTO apartment;
    private House house;
   private String photoId;

    public UserDTO() {}

    public UserDTO(Integer userId, String firstName, String lastName, String email, Integer osbbId, Apartment apartment, House house, String gender, String password, String photoId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.osbbId = osbbId;
        this.apartment= ApartmentDTOMapper.mapApartmentEntityToDTO(apartment);
        this.house = house;
        this.gender = gender;
        this.password = password;
        this.photoId = photoId;
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.house = user.getHouse();
        this.password = user.getPassword();
        if(user.getOsbb() != null) {
            this.osbbId = user.getOsbb().getOsbbId();
        }
        this.apartment= ApartmentDTOMapper.mapApartmentEntityToDTO(user.getApartment());
        this.photoId = user.getPhotoId();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ApartmentDTO getApartment() {
        return apartment;
    }
    
	public void setApartment(ApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Integer getOsbbId() {
		return osbbId;
	}

	public void setOsbbId(Integer osbbId) {
		this.osbbId = osbbId;
	}
	
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", osbbId=" + osbbId +
                ", gender='" + gender + '\'' +
                ", photoId='" + photoId + '\'' +
                '}';
    }
}
