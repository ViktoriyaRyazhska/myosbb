package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.UserDTO;
import com.softserve.osbb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 16.08.2016.
 * modified by cavayman 23.09.2016
 */
@Component
public class UserDTOMapper {

    @Autowired
    static ApartmentDTOMapper apartmentDTOMapper;

    public static UserDTO mapUserEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if(user != null) {
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setBirthDate(user.getBirthDate());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setOsbbId(user.getOsbb().getOsbbId());
            userDTO.setGender(user.getGender());
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserEntityToDTO(List<User> usersList) {
        List<UserDTO> usersDTOList = new ArrayList<>();
        if(usersList != null) {
            for(User u: usersList) {
                usersDTOList.add(mapUserEntityToDTO(u));
            }
        }
        return usersDTOList;
    }
    public static User mapUserDtoToEntity(User user,UserDTO userDTO) {
        if(userDTO!=null){
            user.setUserId(userDTO.getUserId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setBirthDate(userDTO.getBirthDate());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setGender(userDTO.getGender());
        }
        return user;
    }
}
