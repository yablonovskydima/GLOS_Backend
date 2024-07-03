package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.*;
import feign.Headers;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "fileStorage")
public interface FileStorageClient
{
    @PostMapping(value = "/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileAndStatus> uploadFiles(@RequestPart(value = "filePath") String filePath, @RequestPart(value = "file") MultipartFile file);
    @PostMapping(value = "/storage/files/download", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    Response downloadFiles(@RequestBody DownloadRequest request);

    @GetMapping(value = "/storage/files/download/{filename}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    Response downloadFile(@PathVariable String filename);

    @PutMapping(value = "/storage/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileAndStatus> updateFile(@ModelAttribute FileWithPath request);

    @PostMapping("/storage/move")
    ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request);

    @DeleteMapping("/storage/delete")
     ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request);

}
