package com.glos.filemanagerservice.facade;

import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.*;
import com.glos.filemanagerservice.entities.*;
import com.glos.filemanagerservice.requestFilters.TagRequestFilter;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import com.pathtools.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@Service
public class TagFacade {

    @Service
    public static class TagFileHandler {
        private final FileClient fileClient;
        private final FileDTOMapper fileDTOMapper;

        public TagFileHandler(
                FileClient fileClient,
                FileDTOMapper fileDTOMapper
        ) {
            this.fileClient = fileClient;
            this.fileDTOMapper = fileDTOMapper;
        }

        public ResponseEntity<?> putTag(Path node, String tagName) {
            String rootFullName = node.getPath();
            final File file = getFileByRootFullName(rootFullName);
            if (file.getTags() == null) {
                file.setTags(new HashSet<>());
            }
            file.getTags().add(new Tag(tagName));
            fileClient.updateFile(file, file.getId());
            return ResponseEntity.noContent().build();
        }

        public ResponseEntity<?> removeTag(Path node, String tagName) {
            String rootFullName = node.getPath();
            final File file = getFileByRootFullName(rootFullName);
            if (file.getTags() != null && !file.getTags().isEmpty()) {
                file.getTags().removeIf(x -> x.getName().equals(tagName));
                fileClient.updateFile(file, file.getId());
            }
            return ResponseEntity.noContent().build();
        }

        private File getFileByRootFullName(String rootFullName) {
            return fileClient.getFileByRootFullName(rootFullName).getBody();
        }

    }

    @Service
    public static class TagRepositoryHandler {
        private final RepositoryClient repositoryClient;
        private final RepositoryDTOMapper repositoryDTOMapper;

        public TagRepositoryHandler(
                RepositoryClient repositoryClient,
                RepositoryDTOMapper repositoryDTOMapper
        ) {
            this.repositoryClient = repositoryClient;
            this.repositoryDTOMapper = repositoryDTOMapper;
        }

        public ResponseEntity<?> putTag(Path node, String tagName) {
            String rootFullName = node.getPath();
            final Repository repository = getRepositoryByRootFullName(rootFullName);
            if (repository.getTags() == null) {
                repository.setTags(new HashSet<>());
            }
            repository.getTags().add(new Tag(tagName));
            return repositoryClient.updateRepository(repository, repository.getId());
        }

        public ResponseEntity<?> removeTag(Path node, String tagName) {
            String rootFullName = node.getPath();
            final Repository repository = getRepositoryByRootFullName(rootFullName);
            if (repository.getTags() != null && !repository.getTags().isEmpty()) {
                repository.getTags().removeIf(x -> x.getName().equals(tagName));
                return repositoryClient.updateRepository(repository, repository.getId());
            }
            return ResponseEntity.noContent().build();
        }

        private Repository getRepositoryByRootFullName(String rootFullName) {
            Repository dto = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
            return dto;
        }
    }

    private final TagFileHandler fileHandler;
    private final TagRepositoryHandler repositoryHandler;
    private final TagClient tagClient;

    public TagFacade(
            TagFileHandler fileHandler,
            TagRepositoryHandler repositoryHandler,
            TagClient tagClient) {
        this.fileHandler = fileHandler;
        this.repositoryHandler = repositoryHandler;
        this.tagClient = tagClient;
    }

    public ResponseEntity<?> putTag(String tagName) {
        return tagClient.ensureTag(tagName);
    }

    public ResponseEntity<?> deleteTag(String tagName) {
        Tag tag = tagClient.getTagByName(tagName).getBody();
        return tagClient.deleteTag(tag.getId());
    }

    public ResponseEntity<?> addTag(Path path, String tagName) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(tagName);
        return switch (path.getLast().getType()) {
            case REPOSITORY -> repositoryHandler.putTag(path, tagName);
            case FILE, ARCHIVE -> fileHandler.putTag(path, tagName);
            default -> throw new IllegalArgumentException();
        };
    }

    public ResponseEntity<?> removeTag(Path path, String tagName) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(tagName);
        return switch (path.getLast().getType()) {
            case REPOSITORY -> repositoryHandler.removeTag(path, tagName);
            case FILE, ARCHIVE -> fileHandler.removeTag(path, tagName);
            default -> throw new IllegalArgumentException();
        };
    }

    public Page<Tag> getTagsByFilter(TagRequestFilter filter) {
        Map<String, Object> map = MapUtils.map(filter);
        return tagClient.getByFilter(map).getBody();
    }

}
