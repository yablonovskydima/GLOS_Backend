package com.glos.api.userservice.responseMappers;

import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.GroupFilterRequest;
import org.springframework.stereotype.Component;

@Component
public class GroupFilterRequestMapper extends AbstractMapper<Group, GroupFilterRequest> {

}
