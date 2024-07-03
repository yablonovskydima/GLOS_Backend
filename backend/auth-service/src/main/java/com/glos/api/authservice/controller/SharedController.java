package com.glos.api.authservice.controller;

import com.glos.api.authservice.dto.SharedTokenResponse;
import com.glos.api.authservice.service.SharedService;
import com.glos.api.authservice.shared.SharedRequest;
import com.glos.api.authservice.util.security.JwtResponse;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class SharedController {

    private final SharedService sharedService;

    public SharedController(SharedService sharedService) {
        this.sharedService = sharedService;
    }

    @PostMapping("/shared/{rootFullName}")
    public ResponseEntity<SharedTokenResponse> generateSharedToken(
            @PathVariable String rootFullName,
            @RequestParam(value = "expired", required = false, defaultValue = "18000000") long expired,
            UriComponentsBuilder uriBuilder)
    {
        final com.pathtools.Path path = new PathParser().parse(rootFullName);

        SharedRequest request = new SharedRequest(expired, rootFullName);
        JwtResponse jwtResponse = sharedService.generateShared(request);
        final String token = jwtResponse.getAccessToken();
        final String baseUrl = getBaseUrlForResource(path);

        final URI sharedUri = uriBuilder.path(baseUrl)
                .queryParam("shared", token)
                .build(path.getPath());

        return ResponseEntity.ok(new SharedTokenResponse(token, sharedUri.toString()));
    }

    @GetMapping("/shared/validate")
    public ResponseEntity<?> validateSharedToken(@RequestParam("token") String token) {
        sharedService.validateShared(token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sys/shared/destroy/{path}")
    public ResponseEntity<?> destroyShared(@PathVariable String path) {
        sharedService.destroyShared(path);
        return ResponseEntity.ok().build();
    }

    private String getBaseUrlForResource( Path path) {
        return switch (path.getLast().getType()) {
            case REPOSITORY -> "/repositories/root-fullname/{rootFullName}";
            case FILE, ARCHIVE -> "/files/root-fullname/{rootFullName}";
            default -> throw new UnsupportedOperationException();
        };
    }

}
