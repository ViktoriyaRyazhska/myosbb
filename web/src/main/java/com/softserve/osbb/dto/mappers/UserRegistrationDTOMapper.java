/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.UserRegistrationDTO;
import com.softserve.osbb.dto.adapter.UserRegistrationToUserAdapter;
import com.softserve.osbb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ndovhuy on 27.10.2016.
 */
@Component
public class UserRegistrationDTOMapper extends AbstractDTOMapper<User, UserRegistrationDTO> {

    @Autowired
    private UserRegistrationToUserAdapter userRegistrationToUserAdapter;

    @Override
    public UserRegistrationDTO mapEntityToDTO(User entity) {
        throw new UnsupportedOperationException();
    }


    @Override
    public User mapDTOToEntity(UserRegistrationDTO dto) {
        return userRegistrationToUserAdapter.parse(dto);
    }
}
