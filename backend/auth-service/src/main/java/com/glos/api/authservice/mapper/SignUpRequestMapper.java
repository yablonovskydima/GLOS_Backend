package com.glos.api.authservice.mapper;

import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.entities.User;
import com.glos.api.authservice.mapper.base.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class SignUpRequestMapper extends AbstractMapper<User, SignUpRequest> {
    @Override
    protected void postDtoCopy(User source, SignUpRequest destination) {
        destination.setPhoneNumber(source.getPhone_number());
        destination.setPassword(source.getPassword_hash());
        destination.setFirstName(source.getFirst_name());
        destination.setLastName(source.getLast_name());
    }

    @Override
    protected void postEntityCopy(SignUpRequest source, User destination) {
        destination.setPhone_number(source.getPhoneNumber());
        destination.setPassword_hash(source.getPassword());
        destination.setFirst_name(source.getFirstName());
        destination.setLast_name(source.getLastName());
    }
}
