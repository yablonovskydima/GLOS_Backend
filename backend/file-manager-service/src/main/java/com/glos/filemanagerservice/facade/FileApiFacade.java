package com.glos.filemanagerservice.facade;

import com.accesstools.AccessNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.*;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.exception.FieldException;
import com.glos.filemanagerservice.exception.HttpStatusCodeImplException;
import com.glos.filemanagerservice.exception.ResourceAlreadyExistsException;
import com.glos.filemanagerservice.exception.ResourceNotFoundException;
import com.glos.filemanagerservice.responseMappers.*;
import com.glos.filemanagerservice.utils.ZipUtil;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.PathParser;
import com.pathtools.pathnode.FilePathNode;
import com.pathtools.pathnode.PathNode;
import com.pathtools.pathnode.PathNodeProps;
import com.pathtools.pathnode.RepositoryPathNode;
import feign.FeignException;
import feign.Response;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileApiFacade {
    private final FileClient fileClient;
    private final FileDTOMapper fileDTOMapper;
    private final FileRequestDTOMapper fileRequestDTOMapper;
    private final FileStorageClient fileStorageClient;
    private final RepositoryStorageClient repositoryStorageClient;

    public FileApiFacade(FileClient fileClient,
                         RepositoryClient repositoryClient,
                         FileDTOMapper fileDTOMapper,
                         FileRequestMapper fileRequestMapper,
                         RepositoryDTOMapper repositoryDTOMapper,
                         FileRequestDTOMapper fileRequestDTOMapper,
                         FileStorageClient fileStorageClient, TagClient tagClient, RepositoryStorageClient repositoryStorageClient) {
        this.fileClient = fileClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileStorageClient = fileStorageClient;
        this.fileRequestDTOMapper = fileRequestDTOMapper;
        this.repositoryStorageClient = repositoryStorageClient;
    }

    public ResponseEntity<List<FileAndStatus>> uploadFiles(List<FileRequest.FileNode> uploadRequests) {
        final List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        final Map<String, Map.Entry<File, MultipartFile>> map = new HashMap<>();
        for (var u : uploadRequests) {
            try {
                if (u.getFile() == null) {
                    throw new FieldException("file field is null", "file");
                } else if (u.getFileData() == null) {
                    throw new FieldException("fileData field is null", "fileData");
                }
                final File file = convertAndComplateFile(u.getFileData(), true);
                file.setRootSize((int) u.getFile().getSize());
                final String mime = Files.probeContentType(java.nio.file.Path.of(file.getDisplayFilename()));
                if (!mime.equals(u.getFile().getContentType())) {
                    throw new FieldException("Multipart file format and entity file format not match", "rootFormat");
                }
                final Map.Entry<File, MultipartFile> entry = Map.entry(file, u.getFile());
                map.put(file.getRootFullName(), entry);
            } catch (JsonProcessingException e) {
                fileAndStatuses.add(new FileAndStatus(u.getFile().getOriginalFilename(), FileOperationStatus.FAILED, "Invalid fileData body"));
            } catch (Exception e) {
                fileAndStatuses.add(new FileAndStatus("?", FileOperationStatus.FAILED, e.getMessage()));
            }
        }

        final List<FileWithPath> fileWithPaths = new ArrayList<>();

        map.forEach((k, v) -> {
            final File file = v.getKey();
            try {
                throwIfExistsInDB(k);
                File created = fileClient.createFile(file).getBody();
                fileWithPaths.add(new FileWithPath(k, v.getValue()));
            } catch (FeignException e) {
                String filename = "?";
                if (file != null && file.getRootFullName() != null) {
                    filename = file.getRootFullName();
                }
                final String message = "%s. Invalid saved file.".formatted(HttpStatus.valueOf(e.status()));
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, message));
            }
        });

        saveToStorage(fileWithPaths, fileAndStatuses);
        return ResponseEntity.ok(fileAndStatuses);
    }

    private void throwIfExistsInDB(String rootFullName) {
        try {
            getFileByRootFullName(rootFullName);
        } catch (FeignException e) {
            HttpStatusCode code = HttpStatusCode.valueOf(e.status());
            if (code.isSameCodeAs(HttpStatus.CONFLICT) ||
                    !code.isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new ResourceAlreadyExistsException("already taken", "rootFullName");
            }
        }
    }

    private void throwIfNotExistsInDB(String rootFullName) {
        try {
            getFileByRootFullName(rootFullName);
        } catch (FeignException e) {
            HttpStatusCode code = HttpStatusCode.valueOf(e.status());
            if (code.isSameCodeAs(HttpStatus.CONFLICT) ||
                    !code.isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new ResourceAlreadyExistsException("already taken", "rootFullName");
            }
        }
    }

    private void saveToStorage(List<FileWithPath> fileWithPaths, List<FileAndStatus> fileAndStatuses) {
        for (FileWithPath file : fileWithPaths) {
            try {
                fileAndStatuses.add(fileStorageClient.uploadFiles(file.getFilePath(), file.getFile()).getBody());
            } catch (FeignException e) {
                undoSaveDb(file.getFilePath(), null);
                String filename = "?";
                if (file.getFilePath() != null) {
                    filename = file.getFilePath();
                }
                final String message = "%s. Invalid saved file.".formatted(HttpStatus.valueOf(e.status()));
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, message));
            }
        }
    }

    private void saveToStorageUpdate(Map<String, Map.Entry<FileWithPath, File>> fileWithPaths, List<FileAndStatus> fileAndStatuses) {
        fileWithPaths.forEach((k, v) -> {
            try {
                fileAndStatuses.add(fileStorageClient.updateFile(v.getKey()).getBody());
            } catch (FeignException e) {
                undoSaveDb(v.getKey().getFilePath(), v.getValue());
                String filename = "?";
                if (v.getKey().getFilePath() != null) {
                    filename = v.getKey().getFilePath();
                }
                final String message = "%s. Invalid saved file.".formatted(HttpStatus.valueOf(e.status()));
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, message));
            }
        });
    }

    private void undoSaveDb(String rootFullName, File roollback) {
        try {
            final File file = getFileByRootFullName(rootFullName);
            if (roollback == null) {
                fileClient.deleteFile(file.getId());
            } else {
                fileClient.updateFile(roollback, file.getId());
            }
        } catch (Exception ignore) {
        }
    }

    public File getFileByRootFullName(String rootFullName) {
        return fileClient.getFileByRootFullName(rootFullName).getBody();
    }

    private File convertAndComplateFile(String json, boolean assignPath) throws JsonProcessingException {
        final FileRequestDTO fileDTO = new ObjectMapper().readValue(json, FileRequestDTO.class);
        final File file = fileRequestDTOMapper.toEntity(fileDTO);
        return complateFile(file, assignPath);
    }

    private File complateFile(File file, boolean assignPath) {
        checkAccessTypes(file);
        file.setCreationDate(LocalDateTime.now());
        if (assignPath) {
            assignPath(file);
        }
        return file;
    }

    public ResponseEntity<List<FileAndStatus>> update(FileUpdateRequest updateRequest) {
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        try {
            MoveRequest moveRequest = new MoveRequest();
            for (FileUpdateRequest.FileNode request : updateRequest.getFiles()) {
                ObjectMapper objectMapper = new ObjectMapper();
                File file = objectMapper.readValue(request.getFileBody(), File.class);
                checkAccessTypes(file);
                file.setUpdateDate(LocalDateTime.now());
                file.setId(request.getId());

                String oldRootFullName = fileClient.getFileByID(request.getId()).getBody().getRootFullName();
                fileClient.updateFile(file, request.getId());

                if (file.getRootFullName() != null && !oldRootFullName.equals(file.getRootFullName())) {
                    MoveRequest.MoveNode moveNode = new MoveRequest.MoveNode(oldRootFullName, file.getRootFullName());
                    moveRequest.getMoves().add(moveNode);
                }

                if (request.getFileData() != null)
                {
                    FileWithPath fileWithPath = new FileWithPath(oldRootFullName, request.getFileData());
                    fileAndStatuses.add(fileStorageClient.updateFile(fileWithPath).getBody());
                }

            }
            if (moveRequest != null)
            {
                fileStorageClient.moveFile(moveRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(fileAndStatuses);
    }

    public ResponseEntity<List<FileAndStatus>> deleteFiles(List<String> rootFullNames) {
        if (rootFullNames == null) {
            throw new IllegalArgumentException("body is null");
        } else if (rootFullNames.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        List<String> existingFiles = new ArrayList<>(rootFullNames);
        for (String rotFullName : rootFullNames) {
            File file;
            try {
                file = fileClient.getFileByRootFullName(rotFullName).getBody();
            } catch (FeignException e) {
                HttpStatusCode code = HttpStatusCode.valueOf(e.status());
                if (code.isSameCodeAs(HttpStatus.NOT_FOUND)) {
                    fileAndStatuses.add(new FileAndStatus(rotFullName, FileOperationStatus.FAILED, "not found"));
                } else {
                    final String message = "%s. Invalid saved file.".formatted(HttpStatus.valueOf(e.status()));
                    fileAndStatuses.add(new FileAndStatus(rotFullName, FileOperationStatus.FAILED, message));

                }
                existingFiles.remove(rotFullName);
                continue;
            }
            try {
                fileClient.deleteFile(file.getId());
            } catch (FeignException e) {
                final String message = "%s. Invalid saved file.".formatted(HttpStatus.valueOf(e.status()));
                fileAndStatuses.add(new FileAndStatus(rotFullName, FileOperationStatus.FAILED, message));
                existingFiles.remove(rotFullName);
            }
        }
        DeleteRequest request = new DeleteRequest(existingFiles);
        fileAndStatuses = fileStorageClient.deleteFile(request).getBody();
        return ResponseEntity.ok(fileAndStatuses);

    }

    public InputStreamResource downloadFiles(DownloadRequest request) {
        Response response = fileStorageClient.downloadFiles(request);
        InputStreamResource inputStreamResource = getInputStreamResource(response);
        return inputStreamResource;
    }

    public Map.Entry<InputStreamResource, File> downloadFileByRootFullName(String rootFullName) throws IOException {
        final Path path = Path.builder(rootFullName).build();
        final File file;
        try {
            file = getFileByRootFullName(path.getPath());
        } catch (FeignException e) {
            HttpStatusCode code = HttpStatusCode.valueOf(e.status());
            if (code.isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new ResourceNotFoundException("not found", "rootFullName");
            }
            throw new HttpStatusCodeImplException(code, "error download file");
        }

        final InputStreamResource inputStreamResource = getInputStreamResource(fileStorageClient.downloadFile(rootFullName));
        return Map.entry(inputStreamResource, file);
    }

    public InputStreamResource downloadFilesByPath(String rootPath)
    {
        final InputStreamResource inputStream = getInputStreamResource(repositoryStorageClient.getRepository(rootPath));
        return inputStream;
    }

    public ResponseEntity<Page<FileDTO>> getFileByRepository(Long repositoryId, int page, int size, String sort) {
//        ResponseEntity<Repository> response = repositoryClient.getRepositoryById(repositoryId);
//        Repository repository = response.getBody();
//        RepositoryDTO repositoryDTO = repositoryDTOMapper.toDto(repository);
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setRepository(repositoryDTO);
//        FileRequestFilter filter = new FileRequestFilter();
//        fileRequestMapper.transferEntityDto(fileDTO, filter);
//        filter.setPage(page);
//        filter.setSize(size);
//        filter.setSort(sort);
        Repository repository = new Repository();
        repository.setId(repositoryId);
        File file = new File();
        file.setRepository(repository);

        //Map<String, Object> map = MapUtils.map(filter);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("sort", sort);
        return ResponseEntity.ok(fileClient.getFilesByFilter(file, map).getBody().map(fileDTOMapper::toDto));
    }

    public ResponseEntity<Page<FileDTO>> getFilesByFilter(File file, int page, int size, String sort) {
        checkAccessTypes(file);

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("sort", sort);
        return ResponseEntity.ok(fileClient.getFilesByFilter(file, map).getBody().map(fileDTOMapper::toDto));
    }

    private void assignPath(File file) {
        if (file.getRootFullName() != null) {
            Path path = PathParser.getInstance().parse(file.getRootPath());
            final FilePathNode node;
            try {
                node = (FilePathNode) path.getLast();
            } catch (Exception e) {
                throw new IllegalArgumentException("Last node of path is not a file");
            }
            file.setRootFullName(node.getRootFullName());
            file.setRootPath(node.getRootPath());
            file.setRootFilename(node.getRootName());
            file.setRootFormat(node.getRootFormat());
            file.setDisplayFullName(path.getSimplePath("/", false));
            file.setDisplayPath(path.reader().parent().getSimplePath("/", false));
            file.setDisplayFilename(node.getSimpleName());
        } else {

            Path path = PathParser.getInstance().parse(file.getRootPath());
            final RepositoryPathNode repositoryPathNode;
            try {
                repositoryPathNode = (RepositoryPathNode) path.getLast();
                if (repositoryPathNode == null) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Parent repository of file not found in path.");
            }
            String rootName = file.getRootFilename();
            if (!rootName.startsWith("%")) {
                rootName = '%' + rootName;
            }
            Path pathFile = PathParser.getInstance().parse(rootName);
            final FilePathNode filePathNode;
            try {
                filePathNode = (FilePathNode) pathFile.getLast();
            } catch (Exception e) {
                throw new IllegalArgumentException("Last node of path is not a file");
            }

            path = path.createBuilder()
                    .node(filePathNode, false)
                    .build();
            file.setRootFilename(path.getLast().getRootName());
            file.setRootPath(path.getLast().getRootPath());
            file.setRootFullName(path.getLast().getRootFullName());
            file.setRootFormat(path.getLast().getRootProp(PathNodeProps.ROOT_FORMAT));
            file.setDisplayFilename(path.getLast().getSimpleName());
            file.setDisplayPath(path.reader().parent().getSimplePath("/", false));
            file.setDisplayFullName(path.getSimplePath("/", false));
            if (file.getRepository() == null) {
                final Repository repository = new Repository();
                repository.setRootPath(repositoryPathNode.getRootPath());
                repository.setRootName(repositoryPathNode.getRootName());
                file.setRepository(repository);
            } else if (file.getRepository().getRootName() == null || file.getRepository().getRootPath() == null) {
                file.getRepository().setRootPath(repositoryPathNode.getRootPath());
                file.getRepository().setRootName(repositoryPathNode.getRootName());
            }
            assignPath(file.getRepository());
        }
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

    private void checkAccessTypes(File file) {
        if (file != null && file.getAccessTypes() != null) {
            file.setAccessTypes(new HashSet<>(
                    file.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            ));
        }
    }

    public ResponseEntity<?> deleteById(Long id) {
        return fileClient.deleteFile(id);
    }

    private InputStreamResource getInputStreamResource(Response response) {
        try {
            return new InputStreamResource(response.body().asInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Internal error");
        }
    }
}
