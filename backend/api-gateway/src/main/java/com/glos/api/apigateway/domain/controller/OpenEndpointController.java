package com.glos.api.apigateway.domain.controller;

import com.glos.api.apigateway.cofing.security.RouteValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OpenEndpointController {

    @GetMapping("/open-endpoints")
    public ResponseEntity<List<String>> getOpenEndpoints() {
        return ResponseEntity.ok(RouteValidator.OPEN_API_ENDPOINTS);
    }

}
