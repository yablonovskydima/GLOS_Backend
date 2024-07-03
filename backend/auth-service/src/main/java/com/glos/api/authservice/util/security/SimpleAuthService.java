package com.glos.api.authservice.util.security;

import com.glos.api.authservice.client.OperationClient;
import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.dto.ChangeRequest;
import com.glos.api.authservice.dto.OperationCreateRequest;
import com.glos.api.authservice.dto.OperationExecuteRequest;
import com.glos.api.authservice.dto.SignInRequest;
import com.glos.api.authservice.entities.Roles;
import com.glos.api.authservice.entities.User;
import com.glos.api.authservice.exception.HttpStatusCodeImplException;
import com.glos.api.authservice.exception.InvalidLoginException;
import com.glos.api.authservice.util.OperationRequests;
import com.glos.api.authservice.util.UsernameUtil;
import feign.FeignException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class SimpleAuthService implements AuthService {

    private final UserAPIClient userAPIClient;
    private final UserDatabaseAPIClient userDatabaseAPIClient;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OperationClient operationClient;

    public SimpleAuthService(
            UserAPIClient userAPIClient,
            UserDatabaseAPIClient userDatabaseAPIClient,
            JwtService jwtService,
            AuthenticationManager authManager,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            OperationClient operationClient
    ) {
        this.userAPIClient = userAPIClient;
        this.userDatabaseAPIClient = userDatabaseAPIClient;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.operationClient = operationClient;
    }

    @Override
    public JwtResponse register(JwtEntity jwtEntity) {
        JwtRequest request = new JwtRequest(jwtEntity.getUsername(), jwtEntity.getPassword());
        User user = jwtEntity.getUser();
        create(user, Roles.fromName(user.getRoles().get(0).getName()));
        return authenticate(request);
    }

    private User create(User user, Roles role) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        ResponseEntity<User> response = userAPIClient.create(user, role.name());
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Faild created user");
        }
        User created = response.getBody();
        created.setPassword_hash(user.getPassword_hash());
        return created;
    }

    public JwtResponse authenticate(SignInRequest request) {
        final String loginType = ("sys".equals(request.getLogin()))
                ? "username"
                : UsernameUtil.detectTypeLogin(request.getLogin());
        final JwtRequest jwtRequest = complateJwtRequest(loginType, request);
        return authenticate(jwtRequest);
    }

    @Override
    public JwtResponse authenticate(JwtRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );

        authentication = authManager.authenticate(authentication);

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        return createResponse(user);
    }

    private User getUserByUsername(String username) {
        ResponseEntity<User> response = userAPIClient.getByUsername(username);

        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("User not found");
        }

        return response.getBody();
    }

    private JwtResponse createResponse(UserDetails user) {
        JwtEntity jwtEntity = (JwtEntity) user;

        String accessToken = jwtService.generateAccessToken(jwtEntity);
        String refreshToken = jwtService.generateRefreshToken(jwtEntity);

        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public JwtResponse refresh(JwtRefreshRequest refreshRequest) {
        return jwtService.refreshUserTokens(refreshRequest.getRefreshToken());
    }

    @Override
    public boolean validate(String token) {
        return jwtService.validateToken(token);
    }

    @Override
    public void execute(OperationExecuteRequest request) {
        operationClient.execute(request);
    }

    @Override
    public void changePassword(String username, ChangeRequest request) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", user.getEmail());
        data.put("oldPassword", request.getOldValue());
        data.put("newPassword", request.getNewValue());
        operationClient.create(OperationRequests.changePassword(null, data));
    }

    @Override
    public void internalChangePassword(String username, ChangeRequest request) {
        final User user = getUserByUsername(username);
        final String newPassword = passwordEncoder.encode(request.getNewValue());
        user.setPassword_hash(newPassword);
        userDatabaseAPIClient.update(user.getId(), user);
    }

    @Override
    public void changePhoneNumber(String username, ChangeRequest request) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", user.getEmail());
        data.put("oldPhoneNumber", request.getOldValue());
        data.put("newPhoneNumber", request.getNewValue());
        operationClient.create(OperationRequests.changePhoneNumber(null, data));
    }

    @Override
    public void changeEmail(String username, ChangeRequest request) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", request.getNewValue());
        data.put("oldEmail", request.getOldValue());
        data.put("newEmail", request.getNewValue());
        operationClient.create(OperationRequests.changeEmail(null, data));
    }

    @Override
    public void changeUsername(String username, ChangeRequest request) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", user.getEmail());
        data.put("newUsername", request.getNewValue());
        operationClient.create(OperationRequests.changeUsername(null, data));
    }

    @Override
    public void deleteAccount(String username) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", user.getEmail());
        operationClient.create(OperationRequests.dropAccount(null, data));
    }

    @Override
    public void restoreAccount(String username) {
        final User user = getUserByUsername(username);
        final Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", user.getEmail());
        operationClient.create(OperationRequests.restoreAccount(null, data));
    }

    private JwtRequest complateJwtRequest(String type, SignInRequest request) {
        final User user = getUserSwitch(type).apply(request.getLogin());
        return new JwtRequest(user.getUsername(), request.getPassword());
    }

    private Function<String, User> getUserSwitch(String type) {
        return switch (type) {
            case "username" -> this::getUserByUsername;
            case "email" -> this::getByEmail;
            case "phoneNumber" -> this::getByPhoneNumber;
            default -> throw new InvalidLoginException("Invalid login", "");
        };
    }

    private User getByUsername(String username) {
        try {
            return userAPIClient.getByUsername(username).getBody();
        } catch (FeignException ex) {
            if (ex.status() >= 500) {
                throw new RuntimeException("Internal server error");
            } else if (ex.status() == 404) {
                throw new UsernameNotFoundException("User not found");
            }
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(ex.status()), ex.getMessage());
        }
    }

    private User getByEmail(String email) {
        try {
            return userAPIClient.getByEmail(email).getBody();
        } catch (FeignException ex) {
            if (ex.status() >= 500) {
                throw new RuntimeException("Internal server error");
            } else if (ex.status() == 404) {
                throw new UsernameNotFoundException("User not found");
            }
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(ex.status()), ex.getMessage());
        }
    }

    private User getByPhoneNumber(String phoneNumber) {
        try {
            return userAPIClient.getByPhoneNumber(phoneNumber).getBody();
        } catch (FeignException ex) {
            if (ex.status() >= 500) {
                throw new RuntimeException("Internal server error");
            } else if (ex.status() == 404) {
                throw new UsernameNotFoundException("User not found");
            }
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(ex.status()), ex.getMessage());
        }
    }
}
