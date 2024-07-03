package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.FileDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.FileService;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/files")
public class FileAPIController
{
    private final FileService fileService;
    private final FileDTOMapper fileDTOMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public FileAPIController(FileService fileService, FileDTOMapper fileDTOMapper,  RepositoryDTOMapper repositoryDTOMapper, UserDTOMapper userDTOMapper) {
        this.fileService = fileService;
        this.fileDTOMapper = fileDTOMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileByID(@PathVariable Long id)
    {
        FileDTO fileDTO = new FileDTO();
        File file = fileService.getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        fileDTO = transferEntityDTO(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file, UriComponentsBuilder uriBuilder)
    {
        Path path = PathParser.getInstance().parse(file.getRootFullName());
        file.setRootFullName(path.getPath());
        File created = fileService.create(file);

        FileDTO fileDTO = new FileDTO();
        fileDTO = transferEntityDTO(file, fileDTO);


        return ResponseEntity
                .created(uriBuilder.path("/files/{id}")
                        .build(created.getId())).body(fileDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@RequestBody File newFile, @PathVariable("id") Long id)
    {
        //PathUtils.ordinalPathsFile(newFile);
        fileService.update(id, newFile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id)
    {
        fileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<?> deleteFile(@PathVariable String rootFullName)
    {
        fileService.deleteByRootFullName(rootFullName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repository/{repositoryId}")
    public  ResponseEntity<Page<FileDTO>> getFilesByRepository(
            @ModelAttribute Repository repository,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        File filter = new File();
        filter.setRepository(repository);
        Page<File> files = fileService.findAllByRepository(filter, pageable);
        Page<FileDTO> fileDTOS = files.map(fileDTOMapper::toDto);
        return ResponseEntity.ok(fileDTOS);
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<FileDTO> getFileByRootFullName(@PathVariable String rootFullName)
    {
        String normalizeRootFullName = PathParser.getInstance().parse(rootFullName).getPath();
        File file = fileService.findByRootFullName(normalizeRootFullName).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        FileDTO fileDTO = new FileDTO();
        fileDTO = transferEntityDTO(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @PutMapping()
    public ResponseEntity<Page<FileDTO>> getFilesByFilter(
            @RequestBody(required = false) File filter,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        if (filter == null) {
            filter = new File();
        } else if (filter.getRootFullName() != null)
        {
            Path path = PathParser.getInstance().parse(filter.getRootFullName());
            filter.setRootFullName(path.getPath());
        }

        Page<File> files = fileService.findAllByFilter(filter, pageable);
        Page<FileDTO> fileDTOS = files.map(fileDTOMapper::toDto);
        return ResponseEntity.ok(fileDTOS);
    }

    FileDTO transferEntityDTO(File source, FileDTO destination)
    {
        RepositoryDTO rep = new RepositoryDTO();
        rep = transferEntityDTORep(source.getRepository(), rep);
        fileDTOMapper.transferEntityDto(source, destination);
        destination.setRepository(rep);
        return destination;
    }
    RepositoryDTO transferEntityDTORep(Repository source, RepositoryDTO destination)
    {
        UserDTO owner = new UserDTO();
        userDTOMapper.transferEntityDto(source.getOwner(), owner);
        repositoryDTOMapper.transferEntityDto(source, destination);
        destination.setOwner(owner);
        return destination;
    }
}
