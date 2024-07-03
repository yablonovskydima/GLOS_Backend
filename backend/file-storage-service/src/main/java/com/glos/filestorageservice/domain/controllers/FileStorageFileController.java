package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.*;
import com.glos.filestorageservice.domain.services.FileStorageService;
import com.glos.filestorageservice.domain.utils.ZipUtil;
import com.pathtools.Path;
import com.pathtools.PathParser;
import com.pathtools.pathnode.FilePathNode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class FileStorageFileController
{

    private final FileStorageService fileStorageService;

    public FileStorageFileController(FileStorageService fileStorageService)
    {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileAndStatus> uploadFile(@RequestPart(value = "filePath") String filePath, @RequestPart(value = "file")MultipartFile file)
    {
        FileAndStatus fileAndStatus;

        try
        {
            fileAndStatus = fileStorageService.upload(filePath, file);
        }
        catch (Exception e)
        {
             throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(fileAndStatus);
    }

    @GetMapping(value = "/files/download/{filename}",
            consumes = "application/json",
            produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("filename") String filename) {
        try {
            final Path path = PathParser.getInstance().parse(filename);
            final FilePathNode fileNode = (FilePathNode) path.reader().last();

            final List<InputStream> filesData = fileStorageService.download(Collections.singletonList(filename));
            if (filesData.isEmpty())
                return ResponseEntity.notFound().build();

            final InputStreamResource resource = new InputStreamResource(filesData.get(0));
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(fileNode.getSimpleName())
                    .build() );

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/files/download")
    public ResponseEntity<InputStreamResource> downloadFiles(@RequestBody DownloadRequest request)
    {
        try
        {
            List<InputStream> filesData = fileStorageService.download(request.getFilenames());

            List<String> fileNames = request.getFilenames().stream()
                    .map(path -> PathParser.getInstance().parse(path).getLast().getSimpleName())
                    .toList();

            InputStream zipData = ZipUtil.createZip(filesData, fileNames);
            InputStreamResource resource = new InputStreamResource(zipData);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("files.zip")
                    .build() );

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFile(@RequestPart(value = "filePath") String filePath, @RequestPart(value = "file")MultipartFile file)
    {
        return ResponseEntity.ok(fileStorageService.update(filePath, file));
    }

    @PostMapping("/move")
    public ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request) throws Exception
    {
        return ResponseEntity.ok(fileStorageService.move(request.getMoves()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request)
    {
        return ResponseEntity.ok(fileStorageService.delete(request.getFilenames()));
    }
}
