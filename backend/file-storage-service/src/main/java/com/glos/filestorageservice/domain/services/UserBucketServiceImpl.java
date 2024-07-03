package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.BucketOperationStatus;
import com.glos.filestorageservice.domain.DTO.UserBucketAndStatus;
import com.glos.filestorageservice.domain.utils.MinioUtil;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserBucketServiceImpl implements UserBucketService
{

    @Autowired
    private  MinioClient minioClient;
    private static final Logger logger = Logger.getLogger("UserBucketServiceImpl");
    @Override
    public UserBucketAndStatus createUserBucket(String username)
    {
        String normilizeUsername = MinioUtil.normalizeBucketName(username);
        logger.info("Creating bucket " + normilizeUsername);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();
        try
        {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(normilizeUsername).build()))
            {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(normilizeUsername).build());
                userBucketAndStatus.setUsername(normilizeUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.CREATED);
                userBucketAndStatus.setMessage("Bucket created successfully");
                logger.info("Bucket " + normilizeUsername + " created successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(normilizeUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.ALREADY_EXISTS);
                userBucketAndStatus.setMessage("Bucket already exists");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(normilizeUsername);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to create bucket " + normilizeUsername);
            throw new RuntimeException(e.getMessage());
        }
        return userBucketAndStatus;
    }

    @Override
    public UserBucketAndStatus deleteUserBucket(String username)
    {
        String normilizeUsername = MinioUtil.normalizeBucketName(username);
        logger.info("Deleting bucket " + normilizeUsername);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();

        try
        {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(normilizeUsername).build()))
            {
                Iterable<Result<Item>> results = minioClient.listObjects(
                        ListObjectsArgs.builder().bucket(normilizeUsername).recursive(true).build()
                );

                for (Result<Item> result : results)
                {
                    Item item = result.get();
                    minioClient.removeObject(
                            RemoveObjectArgs.builder().bucket(normilizeUsername).object(item.objectName()).build()
                    );
                }

                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(normilizeUsername).build());

                userBucketAndStatus.setUsername(normilizeUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.DELETED);
                userBucketAndStatus.setMessage("Bucket deleted successfully");
                logger.info("Bucket " + normilizeUsername + " deleted successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(normilizeUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.DOES_NOT_EXIST);
                userBucketAndStatus.setMessage("Bucket does not exist");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(normilizeUsername);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to delete bucket " + normilizeUsername);
            throw new RuntimeException(e.getMessage());
        }

        return userBucketAndStatus;
    }

    @Override
    public UserBucketAndStatus updateUserBucket(String oldUsername, String newUsername)
    {
        String normilizeOldUsername = MinioUtil.normalizeBucketName(oldUsername);
        String normilizeNewUsername = MinioUtil.normalizeBucketName(newUsername);
        logger.info("Renaming bucket " + normilizeOldUsername + " to " + normilizeNewUsername);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();

        try
        {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(normilizeOldUsername).build()))
            {
                createUserBucket(normilizeNewUsername);

                Iterable<Result<Item>> results = minioClient.listObjects(
                        ListObjectsArgs.builder().bucket(normilizeOldUsername).recursive(true).build()
                );

                for (Result<Item> result : results)
                {
                    Item item = result.get();
                    minioClient.copyObject(
                            CopyObjectArgs.builder()
                                    .bucket(normilizeNewUsername)
                                    .object(item.objectName())
                                    .source(CopySource.builder().bucket(normilizeOldUsername).object(item.objectName()).build())
                                    .build()
                    );
                }

                deleteUserBucket(normilizeOldUsername);
                userBucketAndStatus.setUsername(normilizeNewUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.UPDATED);
                userBucketAndStatus.setMessage("Bucket renamed from " + normilizeOldUsername + " to " + normilizeNewUsername + " successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(normilizeOldUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.DOES_NOT_EXIST);
                userBucketAndStatus.setMessage("Bucket does not exist");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(normilizeOldUsername);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to update bucket " + normilizeOldUsername);
            throw new RuntimeException(e.getMessage());
        }

        return userBucketAndStatus;
    }
}
