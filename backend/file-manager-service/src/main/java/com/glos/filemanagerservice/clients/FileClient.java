package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "files")
public interface FileClient
{
    @GetMapping("/{id}")
    ResponseEntity<File> getFileByID(@PathVariable Long id);

    @PostMapping
    ResponseEntity<File> createFile(@RequestBody File file);

    @PutMapping("/{id}")
    ResponseEntity<?> updateFile(@RequestBody File newFile, @PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFile(@PathVariable Long id);

    @PutMapping
    ResponseEntity<Page<File>> getFilesByFilter(@ModelAttribute File file, @SpringQueryMap Map<String, Object> map);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<File> getFileByRootFullName(@PathVariable String rootFullName);
}
