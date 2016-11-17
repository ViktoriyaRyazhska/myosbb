/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.adapter;

import com.softserve.osbb.dto.UserRegistrationDTO;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ndovhuy on 27.10.2016.
 */
@Component
public class UserRegistrationToUserAdapter extends DTOToEntityAdapter<UserRegistrationDTO, User> {

    @Autowired
    private ApartmentService apartmentService;
    
    @Autowired
    private OsbbService osbbService;
    
    @Autowired
    private RoleService roleService;

    public UserRegistrationToUserAdapter() { }

    @Override
    public User parse(UserRegistrationDTO dtoToParse) {
        User user;
        
        if (dtoToParse == null) {
            if (dto == null) {
                throw new IllegalArgumentException("no dto object was provided for parsing");
            }
            
            UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) dto;
            user = new User();
            _parse(user, userRegistrationDTO);
        } else {
            user = new User();
            _parse(user, dtoToParse);
        }
        
        return user;
    }

    private void _parse(User user, UserRegistrationDTO userRegistrationDTO) {
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setBirthDate(userRegistrationDTO.getBirthDate());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setGender(userRegistrationDTO.getGender());
        user.setActivated(userRegistrationDTO.isActivated());
        user.setRole(roleService.getRole(userRegistrationDTO.getRole()));
        user.setApartment(apartmentService.findOneApartmentByID(userRegistrationDTO.getApartmentId()));
        user.setOsbb(osbbService.getOsbb(userRegistrationDTO.getOsbbId()));
    }

}
