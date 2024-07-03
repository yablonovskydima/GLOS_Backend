package com.glos.api.operationservice.client;

import com.glos.api.operationservice.dto.ChangeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth")
public interface AuthClient {

    @PutMapping("/sys/auth/{username}/change-password")
    ResponseEntity<?> changePassword(@PathVariable("username") String username,
                                     @RequestBody ChangeRequest request);

}
