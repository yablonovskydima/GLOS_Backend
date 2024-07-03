package com.glos.commentservice.domain.facade;

import com.glos.commentservice.domain.DTO.*;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.client.FileApiClient;
import com.glos.commentservice.domain.client.RepositoryApiClient;
import com.glos.commentservice.domain.client.UserApiClient;
import com.glos.commentservice.domain.responseMappers.FileDTOMapper;
import com.glos.commentservice.domain.responseMappers.RepositoryDTOMapper;
import com.glos.commentservice.entities.Comment;
import com.glos.commentservice.entities.File;
import com.glos.commentservice.entities.Repository;
import com.glos.commentservice.entities.User;
import com.pathtools.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CommentApiFacade
{

    @Service
    public static class CommentFileHandler {
        private final FileApiClient fileApiClient;
        private final FileDTOMapper fileDTOMapper;

        public CommentFileHandler(
                FileApiClient fileApiClient,
                FileDTOMapper fileDTOMapper) {
            this.fileApiClient = fileApiClient;
            this.fileDTOMapper = fileDTOMapper;
        }

        public Comment createComment(Path path, Comment comment) {
            final FileDTO fileDTO = getFileByRootFullName(path.getPath());
            final File file = fileDTOMapper.toEntity(fileDTO);
            file.getComments().add(comment);
            fileApiClient.updateFile(file, file.getId());
            return comment;
        }

        private FileDTO getFileByRootFullName(String rootFullName) {
            return fileApiClient.getFileByRootFullName(rootFullName).getBody();
        }

    }

    @Service
    public static class CommentRepositoryHandler {
        private final RepositoryApiClient repositoryApiClient;
        private final RepositoryDTOMapper repositoryDTOMapper;

        public CommentRepositoryHandler(
                RepositoryApiClient repositoryApiClient,
                RepositoryDTOMapper repositoryDTOMapper) {
            this.repositoryApiClient = repositoryApiClient;
            this.repositoryDTOMapper = repositoryDTOMapper;
        }

        public Comment createComment(Path path, Comment comment) {
            final RepositoryDTO repositoryDTO = getRepositoryByRootFullName(path.getPath());
            final Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
            repository.getComments().add(comment);
            repositoryApiClient.updateRepository(repository, repository.getId());
            return comment;
        }

        private RepositoryDTO getRepositoryByRootFullName(String rootFullName) {
            return repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
        }

    }

    private final ExternalCommentApi externalCommentApi;
    private final UserApiClient userApiClient;
    private final CommentFileHandler fileHandler;
    private final CommentRepositoryHandler repositoryHandler;

    public CommentApiFacade(
            ExternalCommentApi externalCommentApi,
            UserApiClient userApiClient,
            CommentFileHandler fileHandler,
            CommentRepositoryHandler repositoryHandler) {
        this.externalCommentApi = externalCommentApi;
        this.userApiClient = userApiClient;
        this.fileHandler = fileHandler;
        this.repositoryHandler = repositoryHandler;
    }

    public Comment getById(Long id)
    {
        return getCommentById(id);
    }

    public Comment createComment(String rootFullName, Comment comment)
    {
        final Path path = Path.builder(rootFullName).build();
        final User author = getUserByUsername(comment.getAuthor().getUsername());
        comment.setAuthor(author);
        comment.setResourcePath(path.getPath());
        Comment created = externalCommentApi.create(comment).getBody();
        return switch (path.getLast().getType()) {
            case FILE, ARCHIVE -> fileHandler.createComment(path, created);
            case REPOSITORY -> repositoryHandler.createComment(path, created);
            default -> throw new IllegalArgumentException();
        };
    }

    public ResponseEntity<?> updateComment(Long id, Comment comment)
    {
        comment.setId(id);
        return externalCommentApi.update(id, comment);
    }


    public ResponseEntity<?> deleteComment(Long id)
    {
        return externalCommentApi.deleteById(id);
    }

    public Page<Comment> getByFilter(String rootFullName, Map<String, Object> filter)
    {
        final Path path = Path.builder(rootFullName).build();
        return externalCommentApi.getAllCommentsByRootFullName(path.getPath(), filter).getBody();
    }

    private User getUserByUsername(String username) {
        return userApiClient.getUserByUsername(username).getBody();
    }

    private Comment getCommentById(Long id) {
        return externalCommentApi.getById(id).getBody();
    }
}
