package com.glos.api.userservice.controllers;

import com.glos.api.userservice.client.RoleAPIClient;
import com.glos.api.userservice.entities.Role;
import com.glos.api.userservice.responseDTO.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users/roles")
public class UserRolesController
{
    private final RoleAPIClient roleAPIClient;

    public UserRolesController(RoleAPIClient roleAPIClient) {
        this.roleAPIClient = roleAPIClient;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name)
    {
        Role role = roleAPIClient.getRoleByName(name).getBody();
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {
        Map<String, Object> filter = new HashMap<>();
        filter.put("page", page);
        filter.put("size", size);
        filter.put("sort", sort);
        Page<Role> rolePage = roleAPIClient.getPage(filter).getBody();
        //Page<RoleDTO> roleDTOPage = rolePage.map(role -> new RoleDTO(role.getName()));
        rolePage.setNumber(page);
        rolePage.setSize(size);
        rolePage.setSortPattern(sort);
        return ResponseEntity.ok(rolePage);

    }
 }
