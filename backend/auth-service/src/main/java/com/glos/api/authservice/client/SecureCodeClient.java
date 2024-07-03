package com.glos.api.authservice.client;

import com.glos.api.authservice.entities.SecureCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "secureCode")
public interface SecureCodeClient
{

    @GetMapping("/secure-codes/path/{path}")
    ResponseEntity<SecureCode> getByResourcePath(@PathVariable("path") String path);

    @PostMapping("/secure-codes")
    ResponseEntity<SecureCode> create(@RequestBody SecureCode code);

    @DeleteMapping("/secure-codes/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);

}