package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.*;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface RepositoryStorageService
{
    RepositoryAndStatus create(String rootFullName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    Map<String, InputStream> download(String rootFullName) throws Exception;
    List<RepositoryAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception;

    RepositoryAndStatus delete(String rootFullName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
