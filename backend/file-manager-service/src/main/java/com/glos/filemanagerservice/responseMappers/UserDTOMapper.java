package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.UserDTO;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {
    @Override
    protected void postDtoCopy(User source, UserDTO destination) {
        destination.setPassword(source.getPassword_hash());
        destination.setPhoneNumber(source.getPhone_number());
        destination.setFirstName(source.getFirst_name());
        destination.setLastName(source.getLast_name());
        destination.setEnabled(source.getIs_enabled());
        destination.setBlocked(source.getIs_account_non_locked());
        destination.setDeleted(source.getIs_deleted());
    }

    @Override
    protected void postEntityCopy(UserDTO source, User destination) {
        destination.setPassword_hash(source.getPassword());
        destination.setPhone_number(source.getPhoneNumber());
        destination.setFirst_name(source.getFirstName());
        destination.setLast_name(source.getLastName());
        destination.setIs_enabled(source.getEnabled());
        destination.setIs_account_non_locked(source.getBlocked());
        destination.setIs_deleted(source.getDeleted());
    }

}
