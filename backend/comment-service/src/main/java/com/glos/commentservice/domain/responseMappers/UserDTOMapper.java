package com.glos.commentservice.domain.responseMappers;

import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.entities.User;
import com.glos.commentservice.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {

}
