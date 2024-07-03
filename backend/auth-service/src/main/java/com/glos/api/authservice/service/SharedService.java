package com.glos.api.authservice.service;

import com.glos.api.authservice.client.SecureCodeClient;
import com.glos.api.authservice.entities.SecureCode;
import com.glos.api.authservice.shared.SharedEntity;
import com.glos.api.authservice.shared.SharedRequest;
import com.glos.api.authservice.util.PathUtils;
import com.glos.api.authservice.util.VerificationCodeGenerator;
import com.glos.api.authservice.util.security.JwtProperties;
import com.glos.api.authservice.util.security.JwtResponse;
import com.glos.api.authservice.util.security.JwtService;
import feign.FeignException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SharedService {

    private final JwtService jwtService;

    private final JwtProperties jwtProps;

    private final SecureCodeClient secureCodeClient;

    public SharedService(
            JwtService jwtService,
            SecureCodeClient secureCodeClient,
            JwtProperties jwtProps
    ) {
        this.jwtService = jwtService;
        this.secureCodeClient = secureCodeClient;
        this.jwtProps = jwtProps;
    }

    public JwtResponse generateShared(SharedRequest sharedEntity) {
        final SecureCode secureCode = ensureSecureCode(sharedEntity);
        final String token = jwtService.generateShared(secureCode);
        return new JwtResponse(token, null);
    }

    private SecureCode ensureSecureCode(SharedRequest sharedRequest) {
        final SecureCode found = getSecureCodeByRootFullName(sharedRequest.getRootFullName());

        if (found != null) {
            if (found.getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("shared token expired");
            }
            return found;
        }

        SharedEntity sharedEntity = new SharedEntity();
        sharedEntity.setRootFullName(sharedRequest.getRootFullName());
        sharedEntity.setExpired(sharedRequest.getExpired());
        final SecureCode completed = completeNewSecureCode(sharedEntity);

        return getCreatedSecureCode(secureCodeClient.create(completed));
    }

    private SecureCode completeNewSecureCode(SharedEntity sharedEntity) {
        final VerificationCodeGenerator generator = new VerificationCodeGenerator();
        final String code = generator.generateVerificationCode();
        final LocalDateTime now = LocalDateTime.now();
        final long expiration = (sharedEntity.getExpired() == null) ? jwtProps.getShared() : sharedEntity.getExpired();
        final LocalDateTime expirationDate = now.plus(Duration.ofMillis(expiration));

        final SecureCode secureCode = new SecureCode();
        secureCode.setResourcePath(sharedEntity.getRootFullName());
        secureCode.setCode(code);
        secureCode.setCreationDate(now);
        secureCode.setExpirationDate(expirationDate);

        return secureCode;
    }

    private SecureCode getSecureCodeByRootFullName(String rootFullName) {
        try {
            final String fullName = PathUtils.normalizeForUrl(rootFullName);
            final ResponseEntity<SecureCode> response = secureCodeClient.getByResourcePath(fullName);
            final HttpStatusCode code = response.getStatusCode();
            if (code.is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (FeignException ignore) {}
        return null;
    }

    private SecureCode getCreatedSecureCode(ResponseEntity<SecureCode> response) {
        try {
            final HttpStatusCode code = response.getStatusCode();
            if (code.is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (FeignException ignore) {}
        return null;
    }

    public boolean validateShared(String token) {
        return jwtService.validateSharedToken(Objects.requireNonNull(token));
    }

    public boolean destroyShared(String path) {
        final SecureCode found = getSecureCodeByRootFullName(path);
        if (found == null) {
            return false;
        }
        ResponseEntity<?> response = secureCodeClient.deleteById(found.getId());
        return response.getStatusCode().is2xxSuccessful();
    }

}
