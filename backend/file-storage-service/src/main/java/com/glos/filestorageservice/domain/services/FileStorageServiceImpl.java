package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.*;
import com.glos.filestorageservice.domain.utils.MinioUtil;
import com.pathtools.PathParser;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = Logger.getLogger("FileStorageServiceImpl");

    @Autowired
    private  MinioClient minioClient;


    @Override
    public FileAndStatus upload(String filePath, MultipartFile file)
    {
        logger.info("Uploading files");
        FileAndStatus fileAndStatus = new FileAndStatus();

        try {
            com.pathtools.Path path = MinioUtil
                    .normilizePath(PathParser
                            .getInstance()
                    .parse(filePath));
            putObject(path, file, -1);
            fileAndStatus = new FileAndStatus(filePath, FileOperationStatus.SAVED, "Successfully saved file");
        }
        catch (Exception e)
        {
            logger.info("Failed to upload file");
            fileAndStatus = new FileAndStatus(filePath, FileOperationStatus.FAILED, "Failed saving file");
        }

        logger.info("Success upload files");
        return fileAndStatus;
    }

    @Override
    public List<InputStream> download(List<String> filenames) throws Exception {
        logger.info("Downloading files");

        List<InputStream> filesData = new ArrayList<>();
        for (String path : filenames)
        {
            com.pathtools.Path pathObj = MinioUtil.normilizePath(
                    PathParser
                            .getInstance()
                            .parse(path));
            try{
                filesData.add(getObject(pathObj));
            } catch (MinioException e) {
                e.printStackTrace();
                throw new Exception("Error while downloading file: " + path, e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("Success downloading files");
        return filesData;
    }

    @Override
    public FileAndStatus update(String filePath, MultipartFile file) {
        logger.info("Updating files");

        com.pathtools.Path path = PathParser.getInstance().parse(filePath);

        // TODO: реалізувати конвертацію (optional)
        /*
        String format = ((FilePathNode)path.reader().last()).getRootFormat();
        MediaType mediaType = null;
        if (format.equals("txt")) {}
        */

        FileAndStatus fileAndStatuses;
        try
        {
            removeObject(path);
            putObject(path, file, -1);
            fileAndStatuses = new FileAndStatus((filePath), FileOperationStatus.UPDATED, "File updated successfully");
        }
        catch (Exception e) {
            logger.info("Failed to update file: " + filePath);
            fileAndStatuses = new FileAndStatus(filePath, FileOperationStatus.FAILED, e.getMessage());
        }

        logger.info("Success updating files");
        return fileAndStatuses;
    }

    @Override
    public List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception {
        logger.info("Move files");
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (var move:moves)
        {
            try
            {
                com.pathtools.Path fromPath = MinioUtil
                        .normilizePath(PathParser
                                .getInstance()
                                .parse(move.getFrom()));
                com.pathtools.Path toPath = MinioUtil
                        .normilizePath(PathParser
                                .getInstance()
                                .parse(move.getTo()));
                copyObject(fromPath, toPath);
                removeObject(fromPath);
                fileAndStatuses.add(new FileAndStatus(move.getFrom(), FileOperationStatus.MOVED, "File moved successful"));
            }
            catch (Exception e)
            {
                logger.info("Failed to move file");
                fileAndStatuses.add(new FileAndStatus(move.getFrom(), FileOperationStatus.FAILED, "File to move file"));
            }
        }

        logger.info("Success move files");
        return fileAndStatuses;
    }

    @Override
    public List<FileAndStatus> delete(List<String> filenames) {
        logger.info("Delete files");
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (String filename:filenames)
        {
            try {
                com.pathtools.Path path = MinioUtil.normilizePath(PathParser.getInstance().parse(filename));
                removeObject(path);
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.DELETED, "File deleted successfully"));
            } catch (Exception e) {
                logger.info("Failed to delete file: " + filename);
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, e.getMessage()));
            }
        }

        logger.info("Success delete files");
        return fileAndStatuses;
    }

    private void putBucket(String bucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketNormilize = bucket;
        boolean existsBucket = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketNormilize)
                .build());
        if (!existsBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketNormilize)
                    .build());
        }
    }

    private InputStream getObject(com.pathtools.Path pathObj) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = MinioUtil.normilizePath(pathObj.createBuilder()
                .removeFirst()
                .build()).getSimplePath("/", false);
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(pathObj.reader().first().getSimpleName())
                        .object(objectPath)
                        .build());
    }

    private void removeObject(com.pathtools.Path path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = MinioUtil
                .normilizePath(path.createBuilder()
                .removeFirst()
                .build()).getSimplePath("/", false);
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .object(objectPath)
                        .build()
        );
    }
    private void putObject(com.pathtools.Path path, MultipartFile file, int partSize) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = MinioUtil
                .normilizePath(path.createBuilder()
                .removeFirst()
                .build()).getSimplePath("/", false);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .object(objectPath)
                        .stream(file.getInputStream(), file.getSize(), partSize)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    private void copyObject(com.pathtools.Path from, com.pathtools.Path to) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String pathObjectTo = MinioUtil
                .normilizePath(to.createBuilder()
                .removeFirst()
                .build()).getSimplePath("/", false);
        final String pathObjectFrom = MinioUtil
                .normilizePath(from.createBuilder()
                .removeFirst()
                .build()).getSimplePath("/", false);
        minioClient.copyObject(
                CopyObjectArgs.builder()
                        .bucket(to.reader().first().getSimpleName())
                        .object(pathObjectTo)
                        .source(CopySource.builder()
                                .bucket(from.reader().first().getSimpleName())
                                .object(pathObjectFrom)
                                .build())
                        .build()
        );
    }
}
