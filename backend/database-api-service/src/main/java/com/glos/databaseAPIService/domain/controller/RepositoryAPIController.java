package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceAlreadyExistsException;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/repositories")
public class RepositoryAPIController
{
    private final RepositoryService repositoryService;
    private final RepositoryDTOMapper mapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public RepositoryAPIController(
            RepositoryService repositoryService,
            RepositoryDTOMapper mapper, UserDTOMapper userDTOMapper) {
        this.repositoryService = repositoryService;

        this.mapper = mapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getRepository(@PathVariable Long id)
    {
        Repository repository = repositoryService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("Repository is not found");} );
        RepositoryDTO repositoryDTO = mapper.toDto(repository);
        return ResponseEntity.of(Optional.of(repositoryDTO));
    }

    @PostMapping
    public ResponseEntity<RepositoryDTO> createRepository(@RequestBody Repository repository, UriComponentsBuilder uriBuilder) {
        Path path = PathParser.getInstance().parse(repository.getRootFullName());
        repository.setRootFullName(path.getPath());

        Repository repo = repositoryService.create(repository);

        RepositoryDTO repositoryDTO = mapper.toDto(repo);

        return ResponseEntity
                .created(uriBuilder.path("/repositories/{id}")
                        .build(repo.getId())).body(repositoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable Long id)
    {
        repositoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id)
    {
        Repository repository = repositoryService.update(id, newRepository);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner-id/{ownerId}")
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable Long ownerId,
                                                                        @ModelAttribute Repository filter,
                                                                        @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
                                                                        @RequestParam(value = "ignoreDefault", required = false, defaultValue = "true") boolean ignoreDefault,
                                                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        if (filter.getOwner() == null) {
            User user = new User();
            user.setId(ownerId);
            filter.setOwner(user);
        } else {
            filter.getOwner().setId(ownerId);
        }
        return getRepositoriesByFilter(filter, ignoreSys, ignoreDefault, pageable);
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable String username,
                                                                        @ModelAttribute Repository filter,
                                                                        @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
                                                                        @RequestParam(value = "ignoreDefault", required = false, defaultValue = "true") boolean ignoreDefault,
                                                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        if (filter.getOwner() == null) {
            User user = new User();
            user.setUsername(username);
            filter.setOwner(user);
        } else {
            filter.getOwner().setUsername(username);
        }
        final Map<String, Object> props = Map.of("ignoreSys", ignoreSys, "ignoreDefault", ignoreDefault);
        return getRepositoriesByFilter(filter, ignoreSys, ignoreDefault, pageable);
    }


    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        Repository rep = repositoryService.findByRootFullName(PathParser.getInstance().parse(rootFullName).getPath()).orElseThrow(() ->
                new ResourceNotFoundException("Repository is not found") );
        RepositoryDTO repositoryDTO = mapper.toDto(rep);
        return ResponseEntity.of(Optional.of(repositoryDTO));
    }

    @PutMapping
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByFilter(
            @RequestBody(required = false) Repository filter,
            @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
            @RequestParam(value = "ignoreDefault", required = false, defaultValue = "true") boolean ignoreDefault,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        if (filter == null) {
            filter = new Repository();
        } else if (filter.getRootFullName() != null) {
            final Path path = PathParser.getInstance().parse(filter.getRootFullName());
            filter.setRootFullName(path.getPath());
        }
        final Map<String, Object> props = Map.of("ignoreSys", ignoreSys, "ignoreDefault", ignoreDefault);
        final Page<Repository> repositories = repositoryService.findAllByFilter(filter, pageable, props);
        final Page<RepositoryDTO> repositoryDTOS = repositories.map(mapper::toDto);
        return ResponseEntity.ok(repositoryDTOS);
    }
}
