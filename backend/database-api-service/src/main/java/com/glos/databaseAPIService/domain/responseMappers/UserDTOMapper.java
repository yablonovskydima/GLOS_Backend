package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO>
{

}
