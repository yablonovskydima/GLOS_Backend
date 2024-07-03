package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.ByteArrayWithPath;
import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import io.minio.errors.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileStorageService {

    FileAndStatus upload(String filePath, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    List<InputStream> download(List<String> filenames) throws Exception;

    FileAndStatus update(String filePath, MultipartFile file);

    List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception;

    List<FileAndStatus> delete(List<String> filenames);

}
