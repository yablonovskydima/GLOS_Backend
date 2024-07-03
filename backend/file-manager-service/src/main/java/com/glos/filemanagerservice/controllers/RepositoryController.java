package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.pathtools.Path;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import static com.glos.filemanagerservice.DTO.RepositoryUpdateRequest.RepositoryNode;

@RestController
@RequestMapping("/repositories")
public class RepositoryController
{
    private final RepositoryClient repositoryClient;
    private final RepositoryApiFacade repositoryApiFacade;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryRequestMapper repositoryRequestMapper;

    public RepositoryController(RepositoryClient repositoryClient,
                                RepositoryApiFacade repositoryApiFacade,
                                RepositoryDTOMapper repositoryDTOMapper,
                                RepositoryRequestMapper repositoryRequestMapper) {
        this.repositoryClient = repositoryClient;
        this.repositoryApiFacade = repositoryApiFacade;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryRequestMapper = repositoryRequestMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getById(@PathVariable Long id)
    {
        Repository repository = repositoryClient.getRepositoryById(id).getBody();
        return ResponseEntity.ok(repositoryDTOMapper.toDto(repository));
    }

    @PostMapping
    public ResponseEntity<RepositoryDTO> create(@Valid @RequestBody RepositoryRequest request)
    {
        Repository repository = repositoryRequestMapper.toEntity(request);
        RepositoryDTO created = repositoryApiFacade.create(repository).getBody();
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody RepositoryUpdateDTO request) {
        final RepositoryNode node = new RepositoryNode(id, request);
        repositoryApiFacade.update(Collections.singletonList(node));
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<List<RepositoryAndStatus>> update(@RequestBody @Valid RepositoryUpdateRequest request) {
        return ResponseEntity.ok(repositoryApiFacade.update(request.getRepositories()).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repositoryApiFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<Page<RepositoryDTO>> getByOwnerId(@PathVariable String username,
                                                            @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                            @RequestParam(name = "shared", required = false) String shared,
                                                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        final Repository repository = new Repository();
        final User user = new User();
        user.setUsername(username);
        repository.setDisplayFullName(search);
        repository.setOwner(user);
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, new HashMap<>(), page, size, sort)
                .getBody());
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        final Repository repository = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        return ResponseEntity.ok(repositoryDTOMapper.toDto(repository));
    }

    @GetMapping("/path/{rootFullName}")
    public ResponseEntity<Page<RepositoryDTO>> getByFilter(@PathVariable String rootFullName,
                                                           @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                           @RequestParam(name = "shared", required = false) String shared,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        final Path path = Path.builder(rootFullName).build();
        final Repository repository = new Repository();
        final User user = new User();
        user.setUsername(path.getFirst().getSimpleName());
        repository.setRootPath(path.getPath());
        repository.setDisplayFullName(search);
        repository.setOwner(user);
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, new HashMap<>(), page, size, sort).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<RepositoryDTO>> getByFilter(@ModelAttribute Repository repository,
                                                           @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                           @RequestParam(name = "username", required = false) String username,
                                                           @RequestParam(name = "shared", required = false) String shared,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        if (username != null) {
            User user = new User();
            user.setUsername(username);
            repository.setOwner(user);
        }
        repository.setDisplayFullName(search);
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, new HashMap<>(), page, size, sort).getBody());
    }

    private void putIfNonNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
