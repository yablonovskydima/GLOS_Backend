package com.glos.api.userservice.client;

import com.glos.api.userservice.responseDTO.UserBucketAndStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "storage")
public interface StorageClient
{
    @PostMapping("/{username}")
     ResponseEntity<UserBucketAndStatus> create(@PathVariable String username);

    @DeleteMapping("/{username}")
    ResponseEntity<UserBucketAndStatus> delete(@PathVariable String username);

    @PutMapping("/{username}/{newUsername}")
    ResponseEntity<UserBucketAndStatus> update(@PathVariable("username") String username,
                                                      @PathVariable("newUsername") String newUsername);
}
