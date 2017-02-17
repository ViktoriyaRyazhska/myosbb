package com.softserve.osbb.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.osbb.dto.UserRegitrationByAdminDTO;
import com.softserve.osbb.dto.adapter.UserByAdminRegistrationAdapter;
import com.softserve.osbb.model.User;

@Component
public class UserRegistrationByAdminDTOMapper extends AbstractDTOMapper<User, UserRegitrationByAdminDTO> {

	@Autowired
	private UserByAdminRegistrationAdapter userRegistrationToUserAdapter;

	@Override
	public UserRegitrationByAdminDTO mapEntityToDTO(User entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public User mapDTOToEntity(UserRegitrationByAdminDTO dto) {
		return userRegistrationToUserAdapter.parse(dto);
	}
}
