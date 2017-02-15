package com.softserve.osbb.dto.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.osbb.dto.UserRegitrationByAdminDTO;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.RoleService;

@Component
public class UserByAdminRegistrationAdapter extends DTOToEntityAdapter<UserRegitrationByAdminDTO, User>{
	
	@Autowired RoleService rs;

	    public User parse(UserRegitrationByAdminDTO dtoToParse) {
	        User user;
	        
	        if (dtoToParse == null) {
	            if (dto == null) {
	                throw new IllegalArgumentException("no dto object was provided for parsing");
	            }
	            
	            UserRegitrationByAdminDTO userRegistrationDTO = (UserRegitrationByAdminDTO) dto;
	            user = new User();
	            _parse(user, userRegistrationDTO);
	        } else {
	            user = new User();
	            _parse(user, dtoToParse);
	        }
	        
	        return user;
	    }

	    private void _parse(User user, UserRegitrationByAdminDTO userRegistrationDTO) {
	        user.setFirstName(userRegistrationDTO.getFirstName());
	        user.setLastName(userRegistrationDTO.getLastName());
	        user.setEmail(userRegistrationDTO.getEmail());
	        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
	        user.setActivated(true);
	        user.setRole(rs.getRole(3));
	        user.setOwner(true);
	    }

}
