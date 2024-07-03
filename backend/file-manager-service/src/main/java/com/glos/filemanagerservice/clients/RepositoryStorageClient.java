package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.MoveRequest;
import com.glos.filemanagerservice.DTO.RepositoryAndStatus;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.glos.filemanagerservice.DTO.ExistsRequest;


import java.util.List;

@FeignClient(name = "repositoryStorage")
public interface RepositoryStorageClient
{
    @PostMapping("/{rootFullName}")
    ResponseEntity<RepositoryAndStatus> createRepository(@PathVariable String rootFullName);

    @GetMapping(value = "/download/{rootFullName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    Response getRepository(@PathVariable String rootFullName);
    @PostMapping("/move")
    ResponseEntity<List<RepositoryAndStatus>> moveRepository(@RequestBody MoveRequest moves);

    @DeleteMapping("/{rootFullName}")
    ResponseEntity<RepositoryAndStatus> deleteRepository(@PathVariable String rootFullName);

    @GetMapping("/exists")
    ResponseEntity<RepositoryAndStatus> exists(@RequestBody ExistsRequest request);
}
