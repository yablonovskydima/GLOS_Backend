package com.glos.api.userservice.responseMappers;

import com.glos.api.userservice.entities.Role;
import com.glos.api.userservice.entities.User;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.UserFilterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserFilterRequestMapper extends AbstractMapper<User, UserFilterRequest> {
    @Override
    protected void postDtoCopy(User source, UserFilterRequest destination) {
        destination.setPhoneNumber(source.getPhone_number());
        destination.setFirstName(source.getFirst_name());
        destination.setLastName(source.getLast_name());
        if (source.getRoles() != null && !source.getRoles().isEmpty()) {
            destination.setRoles(source.getRoles().stream()
                    .map(Role::getName)
                    .toList());
        }
        destination.setEnabled(source.getIs_enabled());
        destination.setDeleted(source.getIs_deleted());
    }

    @Override
    protected void postEntityCopy(UserFilterRequest source, User destination) {
        destination.setPhone_number(source.getPhoneNumber());
        destination.setFirst_name(source.getFirstName());
        destination.setLast_name(source.getLastName());
        if (source.getRoles() != null && !source.getRoles().isEmpty()) {
            destination.setRoles(source.getRoles().stream().map((x) -> {
                Role r = new Role();
                r.setName(x);
                return  r;
            }).toList());
        }
        destination.setIs_enabled(source.getEnabled());
        destination.setIs_deleted(source.getDeleted());
    }
}
