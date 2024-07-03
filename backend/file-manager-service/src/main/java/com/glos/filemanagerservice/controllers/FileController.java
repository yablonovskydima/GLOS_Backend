package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController
{
    private final FileClient fileClient;
    private final FileApiFacade fileApiFacade;
    private final FileDTOMapper fileDTOMapper;


    public FileController(FileClient fileClient,
                          FileApiFacade fileApiFacade,
                          FileDTOMapper fileDTOMapper) {
        this.fileClient = fileClient;
        this.fileApiFacade = fileApiFacade;
        this.fileDTOMapper = fileDTOMapper;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable Long id)
    {
        final File file = fileClient.getFileByID(id).getBody();
        return ResponseEntity.ok(fileDTOMapper.toDto(file));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<List<FileAndStatus>> update(@ModelAttribute FileUpdateRequest updateRequest)
    {
        return ResponseEntity.ok(fileApiFacade.update(updateRequest).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        return ResponseEntity.ok(fileApiFacade.deleteById(id).getBody());
    }

    @DeleteMapping()
    public ResponseEntity<List<FileAndStatus>> delete(@RequestBody List<String> rootFullNames)
    {
        return ResponseEntity.ok(fileApiFacade.deleteFiles(rootFullNames).getBody());
    }

    @GetMapping("/{rootFullName}")
    public ResponseEntity<FileDTO> getByRootFullName(@PathVariable String rootFullName)
    {
        final File file = fileClient.getFileByRootFullName(rootFullName).getBody();
        return ResponseEntity.ok(fileDTOMapper.toDto(file));
    }

    @GetMapping("/repository/{repositoryId}")
    public ResponseEntity<Page<FileDTO>> getByRepository(@PathVariable Long repositoryId,
                                                         @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                         @RequestParam(name = "shared", required = false) String shared,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                         @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {
        Repository repository = new Repository();
        repository.setId(repositoryId);
        File file = new File();
        file.setRepository(repository);
        file.setDisplayFullName(search);
        return ResponseEntity.ok(fileApiFacade.getFilesByFilter(file, page, size, sort).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<FileDTO>> getByFilter(@ModelAttribute File file,
                                                     @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                     @RequestParam(name = "shared", required = false) String shared,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                     @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {
        file.setDisplayFullName(search);
        return ResponseEntity.ok(fileApiFacade.getFilesByFilter(file, page, size, sort).getBody());
    }
}
