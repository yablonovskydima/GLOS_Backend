package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Role;
import com.glos.databaseAPIService.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/roles")
public class RoleAPIController
{
    private final RoleService roleService;

    @Autowired
    public RoleAPIController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id)
    {
        return ResponseEntity.of(roleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role, UriComponentsBuilder uriBuilder)
    {
        Role r = roleService.create(role);
        return ResponseEntity.created(
                uriBuilder.path("/roles/{id}")
                        .build(r.getId())).body(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id)
    {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Role newRole)
    {
        roleService.update(id, newRole);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name)
    {
        return ResponseEntity.of(roleService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getPage(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        Page<Role> roles = roleService.getPage(pageable);
        return ResponseEntity.ok(roles);
    }
}
