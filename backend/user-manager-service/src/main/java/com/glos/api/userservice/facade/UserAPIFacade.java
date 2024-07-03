package com.glos.api.userservice.facade;

import com.glos.api.userservice.client.OperationClient;
import com.glos.api.userservice.client.RoleAPIClient;
import com.glos.api.userservice.client.StorageClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.entities.Role;
import com.glos.api.userservice.entities.User;
import com.glos.api.userservice.exeptions.HttpStatusCodeImplException;
import com.glos.api.userservice.exeptions.ResourceAlreadyExistsException;
import com.glos.api.userservice.exeptions.ResourceNotFoundException;
import com.glos.api.userservice.responseDTO.ChangeRequest;
import com.glos.api.userservice.responseDTO.OperationCreateRequest;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseDTO.UserFilterRequest;
import com.glos.api.userservice.responseMappers.UserFilterRequestMapper;
import com.glos.api.userservice.utils.Constants;
import feign.FeignException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.glos.api.userservice.utils.MapUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAPIFacade {

    private final UserAPIClient userAPIClient;
    private final RoleAPIClient roleAPIClient;
    private final UserFilterRequestMapper userFilterRequestMapper;
    private final StorageClient storageClient;
    private final OperationClient operationClient;


    public UserAPIFacade(
            UserAPIClient userAPIClient,
            RoleAPIClient roleAPIClient,
            UserFilterRequestMapper userFilterRequestMapper,
            StorageClient storageClient, OperationClient operationClient) {
        this.userAPIClient = userAPIClient;
        this.roleAPIClient = roleAPIClient;
        this.userFilterRequestMapper = userFilterRequestMapper;
        this.storageClient = storageClient;
        this.operationClient = operationClient;
    }

    public User getById(Long id) {
        return getUser(userAPIClient.getById(id));
    }
    public User getUserByUsername(String username) {
        return getUser(userAPIClient.getUserByUsername(username));
    }
    public User getEmail(String email) {
        return getUser(userAPIClient.getUserByEmail(email));
    }
    public User getUserByPhoneNumber(String phoneNumber) {
        return getUser(userAPIClient.getUserByPhoneNumber(phoneNumber));
    }

    public ResponseEntity<User> create(User user, String role) {
        user.setRoles(Collections.singletonList(getRole(role)));
        user.setIs_account_non_expired(Constants.DEFAULT_IS_ACCOUNT_NON_EXPIRED);
        user.setIs_account_non_locked(Constants.DEFAULT_IS_ACCOUNT_NON_LOCKED);
        user.setIs_credentials_non_expired(Constants.DEFAULT_IS_CREDENTIALS_NON_EXPIRED);
        user.setIs_enabled(Constants.DEFAULT_IS_ENABLED);
        user.setIs_deleted(Constants.DEFAULT_IS_DELETED);
        ResponseEntity<User> userResponseEntity;

        try
        {
            userResponseEntity = userAPIClient.create(user);
        }
        catch (FeignException e)
        {
            if (e.status() >= 500) {
                throw new RuntimeException("Internal server error");
            } else if (e.status() == 409) {
                throw new ResourceAlreadyExistsException(e.getMessage());
            }
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(e.status()), e.getMessage());
        }

        storageClient.create(user.getUsername());

        //sendConfirmEmail(user);

        return userResponseEntity;
    }

    @Async
    public void sendConfirmEmail(User user) {
        final Map<String,String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        final OperationCreateRequest request = new OperationCreateRequest("confirm-email", map, 60 * 5);
        operationClient.create(request);
    }

    public ResponseEntity<?> change(String property, String username, ChangeRequest request) {
        if (property == null || changeRequestIsNull(request)) {
            return ResponseEntity.badRequest().build();
        } else if (request.oldEqualNew()) {
            return ResponseEntity.status(409).build();
        }
        final User user = getUserByUsername(username);

        if (property.equals("username")) {
            if (!Constants.USERNAME_PATTERN.matcher(request.getNewValue()).find()) {
                throw new IllegalArgumentException("Invalid username format.");
            } else if (!user.getUsername().equals(request.getOldValue())){
                throw new IllegalArgumentException("Old value not equal value in object");
            }

            tryGet(property, () -> userAPIClient.getUserByUsername(request.getNewValue()));

            user.setUsername(request.getNewValue());
        } else if (property.equals("email")) {
            if (!Constants.EMAIL_PATTERN.matcher(request.getNewValue()).find()) {
                throw new IllegalArgumentException("Invalid email format.");
            } else if (!user.getEmail().equals(request.getOldValue())){
                throw new IllegalArgumentException("Old value not equal value in object");
            }

            tryGet(property, () -> userAPIClient.getUserByEmail(request.getNewValue()));

            user.setEmail(request.getNewValue());
        } else if (property.equals("phoneNumber")) {
            if (!Constants.PHONE_NUMBER_PATTERN.matcher(request.getNewValue()).find()) {
                throw new IllegalArgumentException("Invalid email format.");
            } else if (!user.getPhone_number().equals(request.getOldValue())){
                throw new IllegalArgumentException("Old value not equal value in object");
            }

            tryGet(property, () -> userAPIClient.getUserByPhoneNumber(request.getNewValue()));

            user.setPhone_number(request.getNewValue());
        }

        return userAPIClient.updateUser(user.getId(), user);
    }

    private boolean changeRequestIsNull(ChangeRequest request) {
        return request == null ||
                request.getNewValue() == null ||
                request.getOldValue() == null;
    }

    private void tryGet(String property, Runnable runnable) {
        try {
            runnable.run();
            throw new ResourceAlreadyExistsException("%s already taken".formatted(property));
        } catch (ResourceAlreadyExistsException e) { throw e; }
        catch (Exception ignore) {}
    }

    private Role getRole(String name) {
        ResponseEntity<Role> response = roleAPIClient.getByName(name);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ResourceNotFoundException("Role not found");
    }

    public ResponseEntity<?> deleteByUsername(String username) {
        User user = getUserByUsername(username);
        return noContent(deleted(user, true));
    }

    public ResponseEntity<?> deleteById(Long id) {
        User user = getById(id);
        return noContent(deleted(user, true));
    }

    public ResponseEntity<?> destroy(Long id) {
        User user = getById(id);
        user.getRoles().clear();
        userAPIClient.updateUser(id, user);
        ResponseEntity<?> response = userAPIClient.delete(user.getId());
        //storageClient.delete(user.getUsername());
        return noContent(response);
    }

    public ResponseEntity<?> updateUser(Long id, User newUser) {
        User user = getById(id);
        newUser.setRoles(newUser.getRoles().stream()
                        .map(x -> roleAPIClient.getByName(x.getName()).getBody())
                        .toList());
        ResponseEntity<?> response = userAPIClient.updateUser(user.getId(), newUser);

//        if (!user.getUsername().equals(newUser.getUsername()))
//        {
//            storageClient.update(user.getUsername(), newUser.getUsername());
//        }

        return noContent(response);
    }

    public Page<User> getAllByFilter(UserFilterRequest filter) {
        User user = userFilterRequestMapper.toEntity(filter);
        Map<String, Object> map = MapUtils.map(user);
        ResponseEntity<Page<User>> response = userAPIClient.getUsersByFilter(map);
        Page<User> page = response.getBody();
        return page;
    }

    public ResponseEntity<?> enabled(String username, boolean isEnabled) {
        return enabled(getUserByUsername(username), isEnabled);
    }

    public ResponseEntity<?> blocked(String username, boolean blocked) {
        return blocked(getUserByUsername(username), blocked);
    }

    private ResponseEntity<?> enabled(User user, boolean isEnabled) {
        user.setIs_enabled(isEnabled);
        if (isEnabled) {
            user.setDisabledDateTime(null);
        } else {
            user.setDisabledDateTime(LocalDateTime.now());
        }
        ResponseEntity<?> response = updateUser(user.getId(), user);
        return noContent(response);
    }

    private ResponseEntity<?> blocked(User user, boolean blocked) {
        user.setIs_account_non_locked(blocked);
        if (blocked) {
            user.setDisabledDateTime(LocalDateTime.now());
        } else {
            user.setBlockedDateTime(null);
        }
        ResponseEntity<?> response = updateUser(user.getId(), user);
        return noContent(response);
    }

    public ResponseEntity<?> restore(String username) {
        User user = getUserByUsername(username);
        deleted(user, false);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> deleted(User user, boolean deleted) {
        user.setIs_deleted(deleted);
        if (deleted) {
            user.setDeletedDateTime(LocalDateTime.now().plus(Constants.DURATION_DELETED_STATE));
        } else {
            user.setDeletedDateTime(null);
        }
        ResponseEntity<?> response = updateUser(user.getId(), user);
        return noContent(response);
    }

    private <T> ResponseEntity<T> ok(ResponseEntity<T> response) {
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    private ResponseEntity<?> noContent(ResponseEntity<?> response) {
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    private User getUser(ResponseEntity<User> getUserResponse) {
        if (getUserResponse.getStatusCode().is2xxSuccessful()) {
            return getUserResponse.getBody();
        }
        throw new ResourceNotFoundException("User not found");
    }

}
