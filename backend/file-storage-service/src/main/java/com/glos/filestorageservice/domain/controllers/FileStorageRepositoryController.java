package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.DTO.RepositoryAndStatus;
import com.glos.filestorageservice.domain.services.RepositoryStorageService;
import com.glos.filestorageservice.domain.utils.ZipUtil;
import com.pathtools.Path;
import com.pathtools.PathParser;
import io.minio.errors.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/storage/repositories")
public class FileStorageRepositoryController
{
    private final RepositoryStorageService repositoryStorageService;

    public FileStorageRepositoryController(RepositoryStorageService repositoryStorageService) {
        this.repositoryStorageService = repositoryStorageService;
    }

    @PostMapping("/{rootFullName}")
    public ResponseEntity<RepositoryAndStatus> createRepository(@PathVariable String rootFullName)
    {
        RepositoryAndStatus repositoryAndStatuses;

        try
        {
            repositoryAndStatuses = repositoryStorageService.create(rootFullName);
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(repositoryAndStatuses);
    }

    @GetMapping("/download/{rootFullName}")
    public ResponseEntity<InputStreamResource> getRepository(@PathVariable String rootFullName) throws Exception
    {
       try {
           Map<String, InputStream> filesData = repositoryStorageService.download(rootFullName);
           Map<String, String> fileNames = new HashMap<>();

           for (String key: filesData.keySet())
           {
               java.nio.file.Path path = java.nio.file.Paths.get(key);
               String filename = path.getFileName().toString();
               fileNames.put(key, filename);
           }

           final InputStream zipFile = ZipUtil.createRepositoryZip(filesData, fileNames);
           final InputStreamResource resource = new InputStreamResource(zipFile);
           final HttpHeaders headers = new HttpHeaders();
           Path path = PathParser.getInstance().parse(rootFullName);
           Path newPath = path.createBuilder().removeFirst().build();
           String zipName = newPath.getSimplePath("/", false);
           headers.setContentDisposition(ContentDisposition.attachment()
                   .filename(zipName + ".zip")
                   .build());
           headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

           return new ResponseEntity<>(resource, headers, HttpStatus.OK);
       }
       catch (Exception e)
       {
           System.out.println(e.getMessage());
           return ResponseEntity.internalServerError().build();
       }
    }

    @PostMapping("/move")
    public ResponseEntity<List<RepositoryAndStatus>> moveRepository(@RequestBody MoveRequest moves) throws Exception {
        return ResponseEntity.ok(repositoryStorageService.move(moves.getMoves()));
    }

    @DeleteMapping("/{rootFullName}")
    public ResponseEntity<RepositoryAndStatus> deleteRepository(@PathVariable String rootFullName)
    {
        RepositoryAndStatus repositoryAndStatuses;

        try
        {
            repositoryAndStatuses = repositoryStorageService.delete(rootFullName);
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(repositoryAndStatuses);
    }
}
