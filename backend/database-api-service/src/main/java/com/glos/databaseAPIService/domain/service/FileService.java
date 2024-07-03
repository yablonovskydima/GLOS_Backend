package com.glos.databaseAPIService.domain.service;


import com.accesstools.AccessNode;
import com.glos.databaseAPIService.domain.entities.*;
import com.glos.databaseAPIService.domain.entityMappers.FileMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.CommentRepository;
import com.glos.databaseAPIService.domain.repository.FileRepository;
import com.glos.databaseAPIService.domain.repository.RepositoryRepository;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 	@author - yablonovskydima
 */
@Service
public class FileService implements CrudService<File, Long>
{
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final AccessTypeService accessTypeService;
    private final TagService tagService;
    private final CommentRepository commentRepository;
    private final RepositoryRepository repositoryRepository;

    @Autowired
    public FileService(
            FileRepository fileRepository,
            FileMapper fileMapper,
            AccessTypeService accessTypeService,
            TagService tagService,
            CommentRepository commentRepository,
            RepositoryRepository repositoryRepository
    ) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.accessTypeService = accessTypeService;
        this.tagService = tagService;
        this.commentRepository = commentRepository;
        this.repositoryRepository = repositoryRepository;
    }

    @Override
    @Transactional
    public File create(File file)
    {
        Objects.requireNonNull(file);
        assignRepository(file);
        assignComments(file);
        assignTags(file);
        assignAccessTypes(file);

        return fileRepository.save(file);
    }

    @Override
    public List<File> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public List<File> getAll(EntityFilter filter) {
       throw  new UnsupportedOperationException();
    }

    @Override
    public Optional<File> getById(Long id)
    {
        return fileRepository.findById(id);
    }

    public Optional<File> getByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }

    @Transactional
    @Override
    public File update(Long id, File newFile)
    {
        Objects.requireNonNull(newFile);
        Path path = PathParser.getInstance().parse(newFile.getRootFullName());
        newFile.setRootFullName(path.getPath());
        if (newFile.getAccessTypes() != null) {
            newFile.setAccessTypes(
                    newFile.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
        File file = getFileByIdOrThrow(id);
        fileMapper.transferEntityDto(newFile, file);
        return this.fileRepository.save(file);
    }

    @Transactional
    @Override
    public void deleteById(Long id)
    {
        delete(getFileByIdOrThrow(id));
    }

    @Transactional
    public void deleteByRootFullName(String rootFullName)
    {
        delete(getFileByRootFullNameOrThrow(rootFullName));
    }

    @Transactional
    private void delete(File file)
    {
        Objects.requireNonNull(file);
        assignAccessTypes(file);
        fileRepository.delete(file);
    }

    File getFileByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
    }

    File getFileByRootFullNameOrThrow(String rootFullName)
    {
        return getByRootFullName(rootFullName).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
    }

    public Page<File> findAllByRepository(File filter, Pageable pageable)
    {
        final ExampleMatcher.GenericPropertyMatcher containing =
                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<File> fileExample = Example.of(filter, ExampleMatcher.matching()
                .withMatcher("displayPath", containing)
                .withMatcher("displayFilename", containing)
                .withMatcher("displayFullName", containing)
                .withIgnoreNullValues());
        return fileRepository.findAll(fileExample, pageable);
    }

    public Page<File> findAllByFilter(File filter, Pageable pageable)
    {
        Objects.requireNonNull(filter);

        assignAccessTypes(filter);
        assignTags(filter);

        final ExampleMatcher.GenericPropertyMatcher containing =
                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<File> fileExample = Example.of(filter, ExampleMatcher.matching()
                        .withMatcher("displayPath", containing)
                        .withMatcher("displayFilename", containing)
                        .withMatcher("displayFullName", containing)
                .withIgnoreNullValues());

        final Page<File> files = fileRepository.findAll(fileExample, pageable);
        files.getContent().stream()
                .filter(x -> filter.getAccessTypes() == null || x.getAccessTypes().containsAll(filter.getAccessTypes()))
                .filter(x -> filter.getComments() == null || x.getComments().containsAll(filter.getComments()))
                .filter(x -> filter.getSecureCodes() == null || x.getSecureCodes().containsAll(filter.getSecureCodes()));
        return files;
    }

    public Optional<File> findByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }


    private File assignRepository(File file) {
        Repository repository = file.getRepository();
        if (repository != null) {
            if (repository.getId() == null) {
                if (repository.getRootFullName() != null) {
                    repository = repositoryRepository.findByRootFullName(repository.getRootFullName())
                            .orElseThrow(() -> new ResourceNotFoundException("Repository not found"));
                    file.setRepository(repository);
                }
            }
        }
        return file;
    }

    private File assignTags(File file) {
        final Set<Tag> tags = file.getTags();
        if (tags != null && !tags.isEmpty()) {
            final Set<Tag> found = tags.stream().map(x -> {
                if (x.getName() != null) {
                    return tagService.ensure(x.getName()).getKey();
                }
                return x;
            }).collect(Collectors.toSet());
            file.setTags(new HashSet<>(found));
        }
        return file;
    }

    private File assignAccessTypes(File file) {
        if (file.getAccessTypes() != null) {
            file.setAccessTypes(
                    file.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
        final Set<AccessType> ats = file.getAccessTypes();
        if (ats != null && !ats.isEmpty()) {
            final Set<AccessType> found = ats.stream().map(x -> {
                if (x.getName() != null) {
                    return accessTypeService.ensure(x.getName()).getKey();
                }
                return x;
            }).collect(Collectors.toSet());
            file.setAccessTypes(new HashSet<>(found));
        }
        return file;
    }

    private File assignComments(File file) {
        final Set<Comment> comments = file.getComments();
        if (comments != null) {
            final Set<Comment> found = comments.stream().map(x -> {
                if (x.getId() != null) {
                    return commentRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Comment not found")
                    );
                }
                return x;
            }).collect(Collectors.toSet());
            file.setComments(new HashSet<>(found));
        }
        return file;
    }
}
