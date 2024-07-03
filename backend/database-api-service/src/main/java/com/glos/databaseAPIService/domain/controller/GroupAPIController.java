package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import com.glos.databaseAPIService.domain.responseMappers.GroupDTOMapper;
import com.glos.databaseAPIService.domain.service.GroupService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */

@RestController
@RequestMapping("/groups")
public class GroupAPIController
{
    private final GroupService groupService;
    private final GroupDTOMapper groupDTOMapper;

    @Autowired
    public GroupAPIController(GroupService groupService, GroupDTOMapper groupDTOMapper) {
        this.groupService = groupService;
        this.groupDTOMapper = groupDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id)
    {
        Group g = groupService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group is not found"));
        GroupDTO groupDTO = groupDTOMapper.toDto(g);
        return ResponseEntity.of(Optional.of(groupDTO));
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody Group group, UriComponentsBuilder uriBuilder)
    {
        Group g = groupService.create(group);
        GroupDTO groupDTO = groupDTOMapper.toDto(g);
        return ResponseEntity.created(
                uriBuilder.path("/groups/{id}")
                        .build(g.getId())).body(groupDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id)
    {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody Group newGroup)
    {
        groupService.update(id, newGroup);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<GroupDTO>> getAllGroups(
            @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        Page<Group> groups = groupService.getPage(pageable, ignoreSys);
        Page<GroupDTO> dtos = groups.map(groupDTOMapper::toDto);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<GroupDTO>> getGroupsByFilters(
            @ModelAttribute Group filter,
            @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        Page<Group> groups = groupService.getPageByFilter(filter, pageable, ignoreSys);
        return ResponseEntity.ok(groups.map(groupDTOMapper::toDto));
    }
}
