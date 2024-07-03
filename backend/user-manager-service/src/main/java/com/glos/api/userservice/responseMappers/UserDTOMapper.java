package com.glos.api.userservice.responseMappers;

import com.glos.api.userservice.entities.User;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {
    @Override
    protected void postDtoCopy(User source, UserDTO destination) {
        destination.setPhoneNumber(source.getPhone_number());
        destination.setFirstName(source.getFirst_name());
        destination.setLastName(source.getLast_name());
        destination.setEnabled(source.getIs_enabled());
        destination.setBlocked(source.getIs_account_non_locked());
        destination.setDeleted(source.getIs_deleted());
    }

    @Override
    protected void postEntityCopy(UserDTO source, User destination) {
        destination.setPhone_number(source.getPhoneNumber());
        destination.setFirst_name(source.getFirstName());
        destination.setLast_name(source.getLastName());
        destination.setIs_enabled(source.getEnabled());
        destination.setIs_account_non_locked(source.getBlocked());
        destination.setIs_deleted(source.getDeleted());
    }

}
