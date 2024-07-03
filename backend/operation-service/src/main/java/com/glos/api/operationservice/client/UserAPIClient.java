package com.glos.api.operationservice.client;

import com.glos.api.operationservice.dto.ChangeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user")
public interface UserAPIClient {

    @PutMapping("/users/{username}/block")
    ResponseEntity<?> blockUser(@PathVariable("username") String username);

    @PutMapping("/users/{username}/unblock")
    ResponseEntity<?> unblockUser(@PathVariable("username") String username);

    @PutMapping("/users/{username}/enable")
    ResponseEntity<?> enableUser(@PathVariable("username") String username);

    @PutMapping("/users/{username}/disable")
    ResponseEntity<?> disableUser(@PathVariable("username") String username);

    @PutMapping("/users/{username}/restore")
    ResponseEntity<?> restoreUser(@PathVariable("username") String username);

    @DeleteMapping("/users/username/{username}")
    ResponseEntity<?> deleteUser(@PathVariable("username") String username);

    @PutMapping("/users/{username}/change/{property}")
    ResponseEntity<?> changeProperty(
            @PathVariable String username,
            @PathVariable String property,
            @RequestBody ChangeRequest request
    );
}
