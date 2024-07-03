package com.glos.accessservice.clients;

import com.glos.accessservice.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserClient {

    @GetMapping("/users/username/{username}")
    ResponseEntity<User> getByUsername(@PathVariable String username);

}
