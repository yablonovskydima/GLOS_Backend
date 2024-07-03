package com.glos.commentservice.domain.client;

import com.glos.commentservice.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users")
public interface UserApiClient
{
    @GetMapping("/username/{username}")
    ResponseEntity<User> getUserByUsername(@PathVariable String username);
}
