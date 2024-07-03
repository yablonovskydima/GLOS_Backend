package com.glos.accessservice.facade;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.clients.FileApiClient;
import com.glos.accessservice.clients.RepositoryApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.File;
import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.exeptions.HttpStatusCodeImplException;
import com.glos.accessservice.exeptions.ResponseEntityException;
import com.glos.accessservice.exeptions.SimpleExceptionBody;
import com.glos.accessservice.exeptions.UserAccessDeniedException;
import com.glos.accessservice.facade.chain.InitAccessChain;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.requestDTO.AccessModel;
import com.glos.accessservice.responseDTO.FileDTO;
import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.responseDTO.Pageable;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import com.glos.accessservice.responseMappers.AccessModelMapper;
import com.glos.accessservice.responseMappers.FileDTOMapper;
import com.glos.accessservice.responseMappers.RepositoryDTOMapper;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.pathnode.PathNode;
import feign.FeignException;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class AccessFacade {

    private static <T> Page<T> getPage(List<T> content, Pageable pageable) {
        final String[] sortParts = pageable.getSort().split(",");

        if (sortParts.length != 2)
            throw new IllegalArgumentException();

        return new Page<>(
                content,
                pageable.getPage(),
                pageable.getSize(),
                content.size(),
                sortParts[0],
                sortParts[1],
                true);
    }

    @Service
    public static class FileAccessHandler {

        private final FileApiClient fileApiClient;
        private final AccessTypeApiClient accessTypeApiClient;
        private final FileDTOMapper fileDTOMapper;
        private final AccessModelMapper accessModelMapper;

        public FileAccessHandler(
                FileApiClient fileApiClient,
                AccessTypeApiClient accessTypeApiClient,
                FileDTOMapper fileDTOMapper,
                AccessModelMapper accessModelMapper
        ) {
            this.fileApiClient = fileApiClient;
            this.accessTypeApiClient = accessTypeApiClient;
            this.fileDTOMapper = fileDTOMapper;
            this.accessModelMapper = accessModelMapper;
        }

        ResponseEntity<?> addAccessArchive(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            FileDTO fileDTO = fileApiClient.getByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            File file = fileDTOMapper.toEntity(fileDTO);
            file.getAccessTypes().add(accessType);
            fileApiClient.update(file.getId(), file);
            return ResponseEntity.noContent().build();
        }

        ResponseEntity<?> addAccessFile(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            FileDTO fileDTO = fileApiClient.getByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            File file = fileDTOMapper.toEntity(fileDTO);
            file.getAccessTypes().add(accessType);
            fileApiClient.update(file.getId(), file);
            return ResponseEntity.noContent().build();
        }

        ResponseEntity<?> removeAccessArchive(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            FileDTO fileDTO = fileApiClient.getByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            File file = fileDTOMapper.toEntity(fileDTO);
            file.getAccessTypes().removeIf((x) -> x.getId().equals(accessType.getId()));
            fileApiClient.update(file.getId(), file);
            return ResponseEntity.noContent().build();
        }

        ResponseEntity<?> removeAccessFile(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            FileDTO fileDTO = fileApiClient.getByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            File file = fileDTOMapper.toEntity(fileDTO);
            file.getAccessTypes().removeIf((x) -> x.getId().equals(accessType.getId()));
            fileApiClient.update(file.getId(), file);
            return ResponseEntity.noContent().build();
        }

        Page<AccessModel> fileAccessList(Path path, Pageable pageable) {
            final ResponseEntity<FileDTO> fileResponse = fileApiClient.getByRootFullName(path.getPath());

            if (!fileResponse.getStatusCode().is2xxSuccessful() || !fileResponse.hasBody())
                throw new ResponseEntityException(fileResponse);

            final FileDTO file = fileResponse.getBody();
            final File fileEntity = fileDTOMapper.toEntity(file);
            final List<AccessType> accessTypes = fileEntity.getAccessTypes();

            if (accessTypes == null || accessTypes.isEmpty())
                return AccessFacade.getPage(new ArrayList<>(), pageable);

            final List<AccessModel> accessModels = accessTypes.stream()
                    .map(accessModelMapper::toDto)
                    .toList();

            return AccessFacade.getPage(accessModels, pageable);
        }
    }

    @Service
    public static class RepositoryAccessHandler {
        private final RepositoryApiClient repositoryApiClient;
        private final AccessTypeApiClient accessTypeApiClient;
        private final RepositoryDTOMapper repositoryDTOMapper;
        private final AccessModelMapper accessModelMapper;

        public RepositoryAccessHandler(
                RepositoryApiClient repositoryApiClient,
                AccessTypeApiClient accessTypeApiClient,
                RepositoryDTOMapper repositoryDTOMapper,
                AccessModelMapper accessModelMapper
        ) {
            this.repositoryApiClient = repositoryApiClient;
            this.accessTypeApiClient = accessTypeApiClient;
            this.repositoryDTOMapper = repositoryDTOMapper;
            this.accessModelMapper = accessModelMapper;
        }

        ResponseEntity<?> addAccessRepository(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            Repository repository = repositoryApiClient.getRepositoryByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            repository.getAccessTypes().add(accessType);

            repositoryApiClient.update(repository.getId(), repository);
            return ResponseEntity.noContent().build();
        }

        ResponseEntity<?> removeAccessRepository(Path path, AccessModel model) {
            AccessType type = accessModelMapper.toEntity(model);
            Repository repository = repositoryApiClient.getRepositoryByRootFullName(path.getPath()).getBody();
            AccessType accessType = accessTypeApiClient.ensure(type.getName()).getBody();
            repository.getAccessTypes().removeIf((x) -> x.getName().equals(accessType.getName()));
            repositoryApiClient.update(repository.getId(), repository);
            return ResponseEntity.noContent().build();
        }

        Page<AccessModel> repositoryAccessList(Path path, Pageable pageable) {
            final ResponseEntity<Repository> repositoryResponse = repositoryApiClient.getRepositoryByRootFullName(path.getPath());

            if (!repositoryResponse.getStatusCode().is2xxSuccessful() || !repositoryResponse.hasBody())
                throw new ResponseEntityException(repositoryResponse);

            final Repository repository = repositoryResponse.getBody();
            final List<AccessType> accessTypes = repository.getAccessTypes();

            if (accessTypes == null || accessTypes.isEmpty())
                return AccessFacade.getPage(new ArrayList<>(), pageable);

            final List<AccessModel> accessModels = accessTypes.stream()
                    .map(accessModelMapper::toDto)
                    .toList();

            return AccessFacade.getPage(accessModels, pageable);
        }
    }

    private final FileAccessHandler fileAccessHandler;
    private final RepositoryAccessHandler repositoryAccessHandler;
    private final InitAccessChain initChain;


    public AccessFacade(
            FileAccessHandler fileAccessHandler,
            RepositoryAccessHandler repositoryAccessHandler,
            InitAccessChain initChain
    ) {
        this.fileAccessHandler = fileAccessHandler;
        this.repositoryAccessHandler = repositoryAccessHandler;
        this.initChain = initChain;
    }

    public ResponseEntity<?> available(AccessRequest accessRequest) {
        try {
            accessRequest.setData(new HashMap<>());
            if (initChain.check(accessRequest)) {
                return ResponseEntity.ok().build();
            } else {
                throw new UserAccessDeniedException("user access denied");
            }
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).body(new SimpleExceptionBody(ex.getMessage(), new HashMap<>()));
        }
    }

    public Page<AccessModel> accessPage(String rootFullName, Pageable pageable) {
        final Path path = Path.builder(rootFullName).build();
        return typeSwitchGetList(path.getLast().getType()).apply(path, pageable);
    }

    public ResponseEntity<?> addAccess(String rootFullName, AccessModel model) {
        final Path path = Path.builder(rootFullName).build();
        return typeSwitch(path.getLast().getType(), "add").apply(path, model);
    }

    public ResponseEntity<?> removeAccess(String rootFullName, AccessModel model) {
        final Path path = Path.builder(rootFullName).build();
        return typeSwitch(path.getLast().getType(), "remove").apply(path, model);
    }

    private BiFunction<Path, AccessModel, ResponseEntity<?>> typeSwitch(NodeType type, String opr) {
        if ("add".equals(opr)) {
            return switch (type) {
                case REPOSITORY -> repositoryAccessHandler::addAccessRepository;
                case ARCHIVE -> fileAccessHandler::addAccessArchive;
                case FILE -> fileAccessHandler::addAccessFile;
                default -> throw new UnsupportedOperationException();
            };
        } else if ("remove".equals(opr)) {
            return switch (type) {
                case REPOSITORY -> repositoryAccessHandler::removeAccessRepository;
                case ARCHIVE -> fileAccessHandler::removeAccessArchive;
                case FILE -> fileAccessHandler::removeAccessFile;
                default -> throw new UnsupportedOperationException();
            };
        }
        throw new IllegalArgumentException();
    }
    private BiFunction<Path, Pageable, Page<AccessModel>> typeSwitchGetList(NodeType type) {
        return switch (type) {
            case REPOSITORY -> repositoryAccessHandler::repositoryAccessList;
            case ARCHIVE, FILE -> fileAccessHandler::fileAccessList;
            default -> throw new UnsupportedOperationException();
        };
    }
}
