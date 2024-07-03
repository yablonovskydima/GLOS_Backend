package com.glos.api.authservice.util.security;

import com.glos.api.authservice.dto.ChangeRequest;
import com.glos.api.authservice.dto.OperationExecuteRequest;

public interface AuthService {

    JwtResponse register(JwtEntity jwtEntity);
    JwtResponse authenticate(JwtRequest jwtRequest);
    JwtResponse refresh(JwtRefreshRequest refreshRequest);
    boolean validate(String token);
    void changePassword(String username, ChangeRequest request);
    void internalChangePassword(String username, ChangeRequest request);
    void changePhoneNumber(String username, ChangeRequest request);
    void changeUsername(String username, ChangeRequest request);
    void changeEmail(String username, ChangeRequest request);
    void deleteAccount(String username);
    void restoreAccount(String username);
    void execute(OperationExecuteRequest request);
}
