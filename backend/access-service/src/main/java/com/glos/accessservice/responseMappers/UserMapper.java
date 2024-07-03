package com.glos.accessservice.responseMappers;

import com.glos.accessservice.entities.User;
import com.glos.accessservice.mappers.AbstractMapper;
import com.glos.accessservice.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<UserDTO, User> {
}
