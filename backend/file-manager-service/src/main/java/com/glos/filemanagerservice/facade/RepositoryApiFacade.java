package com.glos.filemanagerservice.facade;

import com.accesstools.AccessNode;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.exception.HttpStatusCodeImplException;
import com.glos.filemanagerservice.exception.ResourceNotFoundException;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestFilterMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryUpdateDTOMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.PathParser;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RepositoryApiFacade
{
    private final RepositoryClient repositoryClient;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryStorageClient repositoryStorageClient;
    private final RepositoryUpdateDTOMapper repositoryUpdateDTOMapper;

    public RepositoryApiFacade(
            RepositoryClient repositoryClient,
            RepositoryDTOMapper repositoryDTOMapper,
            RepositoryStorageClient repositoryStorageClient,
            RepositoryUpdateDTOMapper repositoryUpdateDTOMapper) {
        this.repositoryClient = repositoryClient;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryStorageClient = repositoryStorageClient;
        this.repositoryUpdateDTOMapper = repositoryUpdateDTOMapper;
    }


    public ResponseEntity<RepositoryDTO> create(Repository repository)
    {
        assignPath(repository);
        checkAccessTypes(repository);

        final Path path = PathParser.getInstance().parse(repository.getRootFullName());
        final Path parentRepository = path.reader().parent(NodeType.REPOSITORY);

        final Repository parentFound = repositoryClient.getRepositoryByRootFullName(parentRepository.getPath()).getBody();

        repository.setAccessTypes(parentFound.getAccessTypes());

        final String ownerUsername = path.getFirst().getSimpleName();
        final User owner = (repository.getOwner() != null) ? repository.getOwner() : new User();

        if (owner.getUsername() == null) {
            owner.setUsername(ownerUsername);
        } else if (!owner.getUsername().equals(ownerUsername)) {
            throw new HttpStatusCodeImplException(HttpStatus.CONFLICT, "Conflict", Map.of("owner.username", "owner.username and first node in path not match"));
        }

        repository.setOwner(owner);

        try
        {
            repository.setCreationDate(LocalDateTime.now());
            repository.setUpdateDate(LocalDateTime.now());
            Repository created = repositoryClient.createRepository(repository).getBody();
            RepositoryDTO createdDTO = repositoryDTOMapper.toDto(created);
            repositoryStorageClient.createRepository(repository.getRootFullName()).getBody();
            return ResponseEntity.ok(createdDTO);
        }
        catch (FeignException e)
        {
            HttpStatusCode status = HttpStatusCode.valueOf(e.status());
            if (status.value() == 409) {
                throw new HttpStatusCodeImplException(status, "Conflict");
            } else if (status.value() == 404) {
                throw new HttpStatusCodeImplException(status, "Not found");
            }
            throw new HttpStatusCodeImplException(status, e.getMessage());
        }
    }

    public ResponseEntity<List<RepositoryAndStatus>> update(List<RepositoryUpdateRequest.RepositoryNode> repositories)
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();
        MoveRequest moveRequest = new MoveRequest();
        for (RepositoryUpdateRequest.RepositoryNode repository : repositories)
        {
            Repository found = repositoryClient.getRepositoryById(repository.getId()).getBody();
            if (found.getDefault() != null && found.getDefault()) {
                if (repositories.size() == 1) {
                    throw new ResourceNotFoundException("not found", "id");
                }
                repositoryAndStatuses.add(new RepositoryAndStatus(
                        found.getDisplayFullName(),
                        RepositoryOperationStatus.FAILED,
                        "Default repository is immutable"));
                continue;
            }
            RepositoryUpdateDTO repoDto = repository.getRepository();
            Repository repo = repositoryUpdateDTOMapper.toEntity(repoDto);

            checkAccessTypes(repo);

            repo.setId(repository.getId());
            repo.setUpdateDate(LocalDateTime.now());



            repo.setRootPath(found.getRootPath());
            if (repo.getRootName() == null || repo.getRootName().isEmpty()) {
                repo.setRootName(found.getRootName());
            }
            assignPath(repo);


            repositoryClient.updateRepository(repo, repository.getId());

            if (repo.getRootFullName() != null && !found.getRootFullName().equals(repo.getRootFullName()))
            {
                moveRequest.getMoves().add(new MoveRequest.MoveNode(found.getRootFullName(), repo.getRootFullName()));
            }
        }
        if (moveRequest.getMoves() != null && !moveRequest.getMoves().isEmpty()) {
            repositoryAndStatuses.addAll(repositoryStorageClient.moveRepository(moveRequest).getBody());
        }


        return ResponseEntity.ok(repositoryAndStatuses);
    }

    public ResponseEntity<?> delete(Long id)
    {
        try {
            repositoryStorageClient.deleteRepository(repositoryClient.getRepositoryById(id).getBody().getRootFullName());
        } catch (FeignException e) {
            throw new RuntimeException("Internal server error");
        }
        try
        {
            repositoryClient.deleteRepository(id);
        }
        catch (FeignException e)
        {
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(e.status()), e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<RepositoryDTO>> getRepositoryByOwnerId(Long ownerId, int page, int size, String sort)
    {
        User userDTO = new User();
        userDTO.setId(ownerId);
        Repository repositoryDTO = new Repository();
        repositoryDTO.setOwner(userDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("sort", sort);
        Page<Repository> repositoryPage = repositoryClient.getRepositoriesByFilter(repositoryDTO, map).getBody();
        return ResponseEntity.ok(repositoryPage.map(repositoryDTOMapper::toDto));
    }

    public ResponseEntity<Page<RepositoryDTO>> getRepositoryByFilter(Repository repository, Map<String, Object> filter, int page, int size, String sort)
    {
        checkAccessTypes(repository);
        Map<String, Object> map = new HashMap<>();

        map.put("ignoreSys", true);
        map.put("ignoreDefault", true);
        Page<Repository> repositories = repositoryClient.getRepositoriesByFilter(repository, map).getBody();
        return ResponseEntity.ok(repositories.map(repositoryDTOMapper::toDto));
    }

    private void assignPath(Repository repository) {
        Path path = PathParser.getInstance().parse(repository.getRootPath());
        String rootName = repository.getRootName();
        if (!rootName.startsWith("$")) {
            rootName = '$' + rootName;
        }
        path = path.createBuilder().repository(rootName, false).build();
        repository.setRootName(path.getLast().getRootName());
        repository.setRootPath(path.getLast().getRootPath());
        repository.setRootFullName(path.getLast().getRootFullName());
        repository.setDisplayName(path.getLast().getSimpleName());
        repository.setDisplayPath(path.reader().parent().getSimplePath("/", false));
        repository.setDisplayFullName(path.getSimplePath("/", false));
    }

    private void checkAccessTypes(Repository repository) {
        if (repository.getAccessTypes() != null) {
            repository.setAccessTypes(
                    new HashSet<>(repository.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).toList())
            );
        }
    }
}
