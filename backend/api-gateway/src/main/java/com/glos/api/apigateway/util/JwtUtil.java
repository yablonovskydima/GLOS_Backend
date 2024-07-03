package com.glos.api.apigateway.util;

import com.glos.api.apigateway.domain.client.AuthClient;
import com.glos.api.apigateway.domain.exception.InvalidAccessTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class JwtUtil {

    private final AuthClient authClient;

    public JwtUtil(AuthClient authClient) {
        this.authClient = authClient;
    }

    public boolean validate(final String token)
        throws InvalidAccessTokenException{
        ResponseEntity<?> validated = authClient.validate(token);
        if (!validated.getStatusCode().is2xxSuccessful()) {
            System.out.println("invalid access...!");
            throw new InvalidAccessTokenException("un authorized access to application", token);
        }
        return true;
    }
}
