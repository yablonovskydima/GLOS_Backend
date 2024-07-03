package com.glos.api.userservice.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.glos.api.userservice.entities.User;
import com.glos.api.userservice.exeptions.InvalidLoginException;
import com.glos.api.userservice.facade.*;
import com.glos.api.userservice.responseDTO.ChangeRequest;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.responseDTO.UserFilterRequest;
import com.glos.api.userservice.responseMappers.UserDTOMapper;
import com.glos.api.userservice.utils.Constants;
import com.glos.api.userservice.utils.UsernameUtil;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserDTOMapper userDTOMapper;
    private final UserAPIFacade userAPIFacade;

    public UserController(
            UserDTOMapper userDTOMapper,
            UserAPIFacade userAPIFacade
    ) {
        this.userDTOMapper = userDTOMapper;
        this.userAPIFacade = userAPIFacade;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIFacade.getById(id)));
    }

    @PostMapping("/{role}")
    public ResponseEntity<UserDTO> create(
            @PathVariable String role,
            @RequestBody User user,
            UriComponentsBuilder uriComponentsBuilder)
    {
        ResponseEntity<User> created = userAPIFacade.create(user, role);
        if (created.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .created(uriComponentsBuilder.path("/users/{username}")
                            .build(created.getBody().getUsername()))
                    .body(userDTOMapper.toDto(created.getBody()));
        }
        return ResponseEntity.status(created.getStatusCode()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        return userAPIFacade.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO newUser)
    {
        User user = userDTOMapper.toEntity(newUser);
        return userAPIFacade.updateUser(id, user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIFacade.getUserByUsername(username)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIFacade.getEmail(email)));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIFacade.getUserByPhoneNumber(phoneNumber)));
    }

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<UserDTO>> getAllByFilter(@ModelAttribute UserFilterRequest filter,
                                                        @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        filter.setUsername(search);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        Page<User> users = userAPIFacade.getAllByFilter(filter);
        return ResponseEntity.ok(users.map(userDTOMapper::toDto));
    }

    @GetMapping("/duration-deleted-state")
    public ResponseEntity<Long> getDurationDeletedState() {
        return ResponseEntity.ok(Constants.DURATION_DELETED_STATE.toMillis());
    }

    @PutMapping("/{username}/change/{property}")
    public ResponseEntity<?> changeProperty(
            @PathVariable String username,
            @PathVariable String property,
            @RequestBody ChangeRequest request
    ) {
        return userAPIFacade.change(property, username, request);
    }

    @PutMapping("/{username}/block")
    public ResponseEntity<?> blockUser(@PathVariable("username") String username)
    {
        return userAPIFacade.blocked(username, true);
    }

    @PutMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable("username") String username)
    {
        return userAPIFacade.blocked(username, false);
    }

    @PutMapping("/{username}/enable")
    public ResponseEntity<?> enableUser(@PathVariable("username") String username)
    {
        return userAPIFacade.enabled(username, true);
    }

    @PutMapping("/{username}/disable")
    public ResponseEntity<?> disableUser(@PathVariable("username") String username)
    {
        return userAPIFacade.enabled(username, false);
    }

    @PutMapping("/{username}/restore")
    public ResponseEntity<?> restoreUser(@PathVariable("username") String username) {
        return userAPIFacade.restore(username);
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> destroyUserByUsername(@PathVariable String username) {
        return userAPIFacade.deleteByUsername(username);
    }

    @GetMapping("/{id}/destroy")
    public ResponseEntity<?> destroyUser(@PathVariable Long id) {
        return userAPIFacade.destroy(id);
    }
}
