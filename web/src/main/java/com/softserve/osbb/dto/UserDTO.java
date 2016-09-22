package com.softserve.osbb.dto;

import com.softserve.osbb.dto.mappers.ApartmentDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Role;
import com.softserve.osbb.model.User;

import java.util.Date;

/**
 * Created by Roman on 16.08.2016.
 */
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private String password;
    private Integer osbbId;
    private String gender;
    private String role;
    private Boolean activated;
    private ApartmentDTO apartment;

    public UserDTO() {}

    public UserDTO(Integer userId, String firstName, String lastName, String email, Integer osbbId,Apartment apartment) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.osbbId=osbbId;
        this.apartment= ApartmentDTOMapper.mapApartmentEntityToDTO(apartment);
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.role=user.getRole().getAuthority();
        this.activated=user.getActivated();
        this.osbbId = user.getOsbb().getOsbbId();
        this.apartment= ApartmentDTOMapper.mapApartmentEntityToDTO(user.getApartment());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Integer getOsbbId() {
        return osbbId;
    }

    public void setOsbbId(Integer osbbId) {
        this.osbbId = osbbId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ApartmentDTO getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentDTO apartment) {
        this.apartment = apartment;
    }
}
