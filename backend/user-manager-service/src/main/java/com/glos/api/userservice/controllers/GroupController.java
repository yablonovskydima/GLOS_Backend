package com.glos.api.userservice.controllers;

import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.facade.GroupAPIFacade;
import com.glos.api.userservice.responseDTO.GroupDTO;
import com.glos.api.userservice.responseDTO.GroupFilterRequest;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseMappers.GroupDTOMapper;
import com.glos.api.userservice.responseMappers.GroupFilterRequestMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping
public class GroupController
{
    private final GroupDTOMapper groupDTOMapper;
    private final GroupAPIFacade groupAPIFacade;
    private final GroupFilterRequestMapper groupFilterRequestMapper;


    public GroupController(
            GroupDTOMapper groupDTOMapper,
            GroupAPIFacade groupAPIFacade,
            GroupFilterRequestMapper groupFilterRequestMapper
    ) {
        this.groupDTOMapper = groupDTOMapper;
        this.groupAPIFacade = groupAPIFacade;
        this.groupFilterRequestMapper = groupFilterRequestMapper;
    }
    @GetMapping("/users/groups/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id)
    {
        return ResponseEntity.of(groupAPIFacade.getById(id)
                .map(groupDTOMapper::toDto));
    }

    @GetMapping("/users/groups")
    public ResponseEntity<Page<GroupDTO>> getGroupsByFilters(@ModelAttribute Group group,
                                                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                             @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                             @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        GroupFilterRequest filter = groupFilterRequestMapper.toDto(group);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        return ResponseEntity.ok(groupAPIFacade.getAllFilter(filter)
                .map(groupDTOMapper::toDto));
    }

    @GetMapping("/users/{username}/groups")
    public ResponseEntity<Page<GroupDTO>> getUsersGroups(@PathVariable("username") String username,
                                                         @ModelAttribute Group group,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                         @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        GroupFilterRequest filter = groupFilterRequestMapper.toDto(group);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        Page<Group> groups = groupAPIFacade.getAllByOwner(username, filter);
        return ResponseEntity.ok(groups.map(groupDTOMapper::toDto));
    }

    @GetMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<GroupDTO> getUsersGroupByName(@PathVariable("username") String username,
                                                     @PathVariable("groupName") String groupName)
    {
        return ResponseEntity.of(groupAPIFacade.getByOwnerAndName(username, groupName)
                .map(groupDTOMapper::toDto));
    }

    @PutMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<GroupDTO> putGroup(@PathVariable("username") String username,
                                             @PathVariable("groupName") String groupName,
                                             @RequestBody Group request, UriComponentsBuilder builder) {
        ResponseEntity<Group> response = groupAPIFacade.putGroup(request, username, groupName);
        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201))) {
            Group created = response.getBody();
            return ResponseEntity.created(builder
                        .path("/users/{username}/groups/{groupName}")
                        .build(created.getOwner().getUsername(), created.getName()))
                        .body(groupDTOMapper.toDto(response.getBody()));
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204))) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    @DeleteMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<?> deleteGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName)
    {
        groupAPIFacade.deleteByOwnerAndName(username, groupName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{username}/groups/{groupName}/add-user/{friendUsername}")
    public ResponseEntity<GroupDTO> appendUser(@PathVariable("username") String username,
                                               @PathVariable("groupName") String groupName,
                                               @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.appendUser(username, groupName, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @DeleteMapping("/users/{username}/groups/{groupName}/delete-user/{friendUsername}")
    public ResponseEntity<GroupDTO> deleteUser(@PathVariable("username") String username,
                                               @PathVariable("groupName") String groupName,
                                               @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.removeUser(username, groupName, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

}
