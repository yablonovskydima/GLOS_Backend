package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.utils.Constants;
import com.glos.filemanagerservice.validation.OnCreate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FileUploadController
{
    private final FileApiFacade fileApiFacade;
    private final RepositoryStorageClient repositoryStorageClient;

    public FileUploadController(FileApiFacade fileClient, RepositoryStorageClient repositoryStorageClient) {
        this.fileApiFacade = fileClient;
        this.repositoryStorageClient = repositoryStorageClient;
    }

    @PutMapping("/files/upload")
    public ResponseEntity<List<FileAndStatus>> uploadFile(@ModelAttribute @Validated(OnCreate.class) FileRequest uploadRequests)
    {
        return fileApiFacade.uploadFiles(uploadRequests.getFiles());
    }

    @GetMapping(value = "/files/download",
            consumes = "application/json",
            produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> downloadFiles(@RequestBody DownloadRequest request)
    {
        final InputStreamResource resource = fileApiFacade.downloadFiles(request);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment()
                        .filename(Constants.DOWNLOAD_ZIP_DEFAULT_NAME)
                .build());
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping(value = "/files/{rootFullName}/download",
            consumes = "application/json",
            produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> downloadFileByPath(@PathVariable String rootFullName) throws IOException {
        final Map.Entry<InputStreamResource, File> resource = fileApiFacade.downloadFileByRootFullName(rootFullName);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(resource.getValue().getDisplayFilename())
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource.getKey());
    }


    @GetMapping(value = "/files/path/{rootPath}/download",
            consumes = "application/json",
            produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> downloadRepository(@PathVariable String rootPath)
    {
        final InputStreamResource resource = fileApiFacade.downloadFilesByPath(rootPath);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(Constants.DOWNLOAD_ZIP_DEFAULT_NAME)
                .build());
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
