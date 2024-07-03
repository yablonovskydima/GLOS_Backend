package com.glos.api.userservice.responseMappers;

import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.responseDTO.UserFilterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserFilterRequestDTOMapper extends AbstractMapper<UserDTO, UserFilterRequest> {

}
