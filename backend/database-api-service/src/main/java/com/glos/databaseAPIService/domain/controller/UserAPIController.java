package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/users")
public class UserAPIController
{
    private final UserService userService;
    private final UserDTOMapper mapper;

    @Autowired
    public UserAPIController(UserService userService, UserDTOMapper mapper)
    {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id)
    {
        UserDTO userDTO = new UserDTO();
        User user = userService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user, UriComponentsBuilder uriBuilder)
    {
        User u = userService.create(user);
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.created(
                uriBuilder.path("/users/{id}")
                        .build(u.getId())).body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser)
    {
        userService.update(id, newUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username)
    {
        User user = userService.findByUsername(username).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)
    {
        User user = userService.findByEmail(email).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        User user = userService.findByPhoneNumber(phoneNumber).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsersByFilter(
            @ModelAttribute User filter,
            @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<User> page = userService.getPageByFilter(filter, pageable, ignoreSys);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }
}
