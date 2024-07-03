package com.glos.api.userservice.controllers;

import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.facade.GroupAPIFacade;
import com.glos.api.userservice.responseDTO.GroupDTO;
import com.glos.api.userservice.responseMappers.GroupDTOMapper;
import com.glos.api.userservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class FriendsController
{
    private final GroupDTOMapper groupDTOMapper;
    private final GroupAPIFacade groupAPIFacade;

    public FriendsController(
            GroupDTOMapper groupDTOMapper,
            GroupAPIFacade groupAPIFacade
    ) {
        this.groupDTOMapper = groupDTOMapper;
        this.groupAPIFacade = groupAPIFacade;
    }

    //ok
    @GetMapping("/users/{username}/friends")
    public ResponseEntity<GroupDTO> getFriends(@PathVariable("username") String username)
    {
        return ResponseEntity.of(
                groupAPIFacade.getByOwnerAndName(username, Constants.FRIENDS_GROUP_NAME)
                        .map(groupDTOMapper::toDto)
        );
    }

    @PutMapping("/users/{username}/friends/add-user/{friendUsername}")
    public ResponseEntity<GroupDTO> appendUser(@PathVariable("username") String username,
                                                       @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.appendUser(username, Constants.FRIENDS_GROUP_NAME, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @DeleteMapping("/users/{username}/friends/delete-user/{friendUsername}")
    public ResponseEntity<GroupDTO> deleteUser(@PathVariable("username") String username,
                                               @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.removeUser(username, Constants.FRIENDS_GROUP_NAME, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
