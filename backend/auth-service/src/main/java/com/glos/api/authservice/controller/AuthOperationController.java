package com.glos.api.authservice.controller;

import com.glos.api.authservice.dto.ChangeRequest;
import com.glos.api.authservice.dto.OperationExecuteRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.util.security.SimpleAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthOperationController {

    private final SignUpRequestMapper signUpRequestMapper;
    private final SimpleAuthService simpleAuthService;

    public AuthOperationController(
            SignUpRequestMapper signUpRequestMapper,
            SimpleAuthService simpleAuthService
    ) {
        this.signUpRequestMapper = signUpRequestMapper;
        this.simpleAuthService = simpleAuthService;
    }

    @PostMapping("/auth/execute")
    public ResponseEntity<?> executeOperation(@RequestBody OperationExecuteRequest request) {
        simpleAuthService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/auth/users/{username}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable("username") String username,
                                            @RequestBody ChangeRequest request)
    {
        if (request.oldEqualNew()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old password match to new password");
        }
        simpleAuthService.changePassword(username, request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/sys/auth/{username}/change-password")
    public ResponseEntity<?> internalChangePassword(@PathVariable("username") String username,
                                                    @RequestBody ChangeRequest request) {
        simpleAuthService.internalChangePassword(username, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/auth/users/{username}/change-phone-number")
    public ResponseEntity<?> changePhoneNumber(@PathVariable("username") String username,
                                               @RequestBody ChangeRequest request)
    {
        if (request.oldEqualNew()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changePhoneNumber(username, request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/auth/users/{username}/change-username")
    public ResponseEntity<?> changeUsername(@PathVariable("username") String username,
                                            @RequestBody ChangeRequest request)
    {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not working yet");
        // TODO: impl rename root repository 
        /*
        if (request.oldEqualNew()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changeUsername(username, request);
        return ResponseEntity.accepted().build();
        */
    }

    @PutMapping("/auth/users/{username}/change-email")
    public ResponseEntity<?> changeEmail(@PathVariable("username") String username,
                                         @RequestBody ChangeRequest request)
    {
        if (request.oldEqualNew()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changeEmail(username, request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/auth/users/{username}/drop-account")
    public ResponseEntity<?> dropAccount(@PathVariable("username") String username)
    {
        simpleAuthService.deleteAccount(username);
        return ResponseEntity.accepted().build();
    }

}
