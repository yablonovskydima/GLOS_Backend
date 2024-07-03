package com.glos.api.apigateway.domain.client;

import com.glos.api.apigateway.domain.dto.JwtRequest;
import com.glos.api.apigateway.domain.dto.JwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "auth")
public interface AuthClient {

    @PostMapping("/auth/register")
    ResponseEntity<JwtResponse> register(@RequestBody JwtRequest request);

    @GetMapping("/auth/validate")
    ResponseEntity<?> validate(@RequestParam("token") String token);

}
