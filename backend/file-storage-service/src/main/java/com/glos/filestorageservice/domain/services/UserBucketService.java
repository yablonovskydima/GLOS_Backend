package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.BucketOperationStatus;
import com.glos.filestorageservice.domain.DTO.UserBucketAndStatus;

public interface UserBucketService
{
    UserBucketAndStatus createUserBucket(String username);
    UserBucketAndStatus deleteUserBucket(String username);
    UserBucketAndStatus updateUserBucket(String oldUsername, String newUsername);

}
