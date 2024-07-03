package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.entities.File;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "files")
public interface FileApiClient
{

    @PutMapping("/{id}")
    ResponseEntity<?> updateFile(@RequestBody File newFile, @PathVariable("id") Long id);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<FileDTO> getFileByRootFullName(@PathVariable String rootFullName);
}
