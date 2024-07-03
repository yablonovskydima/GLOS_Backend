package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.UserBucketAndStatus;
import com.glos.filestorageservice.domain.services.UserBucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage/buckets")
public class UserBucketController
{
    private final UserBucketService userBucketService;


    public UserBucketController(UserBucketService userBucketService) {
        this.userBucketService = userBucketService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<UserBucketAndStatus> create(@PathVariable String username)
    {
        UserBucketAndStatus userBucketAndStatus;
        try
        {
            userBucketAndStatus = userBucketService.createUserBucket(username);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(userBucketAndStatus);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<UserBucketAndStatus> delete(@PathVariable String username)
    {
        UserBucketAndStatus userBucketAndStatus;
        try
        {
            userBucketAndStatus = userBucketService.deleteUserBucket(username);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(userBucketAndStatus);
    }

    @PutMapping("/{username}/{newUsername}")
    public ResponseEntity<UserBucketAndStatus> update(@PathVariable("username") String username,
                                                      @PathVariable("newUsername") String newUsername)
    {
        UserBucketAndStatus userBucketAndStatus;
        try
        {
            userBucketAndStatus = userBucketService.updateUserBucket(username, newUsername);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(userBucketAndStatus);
    }
}
