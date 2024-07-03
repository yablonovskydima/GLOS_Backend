package com.glos.databaseAPIService.domain.service;

import com.accesstools.AccessNode;
import com.glos.databaseAPIService.domain.entities.*;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceAlreadyExistsException;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 	@author - yablonovskydima
 */
@Service
public class RepositoryService
{
    private final RepositoryRepository repository;
    private final UserRepository userRepository;
    private final RepositoryMapper repositoryMapper;
    private final AccessTypeRepository accessTypeRepository;
    private final CommentRepository commentRepository;
    private final FileRepository fileRepository;
    private final AccessTypeService accessTypeService;
    private final TagService tagService;

    @Autowired
    private EntityManager entityManager;


    @Autowired
    public RepositoryService(RepositoryRepository repository,
                             RepositoryMapper repositoryMapper,
                             UserRepository userRepository,
                             AccessTypeRepository accessTypeRepository,
                             CommentRepository commentRepository,
                             TagRepository tagRepository,
                             FileRepository fileRepository,
                             AccessTypeService accessTypeService,
                             TagService tagService
    ) {
        this.repository = repository;
        this.repositoryMapper = repositoryMapper;
        this.userRepository = userRepository;
        this.accessTypeRepository = accessTypeRepository;
        this.commentRepository = commentRepository;
        this.tagService = tagService;
        this.fileRepository = fileRepository;
        this.accessTypeService = accessTypeService;
    }

    @Transactional
    public Repository create(Repository repository)
    {
        if (findByRootFullName(repository.getRootFullName()).isPresent()) {
            throw new ResourceAlreadyExistsException(Map.entry("", ""));
        }
        assignUser(repository);
        assignAccessTypes(repository);
        assignTags(repository);

        Repository repo =  this.repository.save(repository);
        return repo;
    }

    public List<Repository> findAllByOwnerId(Long ownerId, Map<String, Object> props)
    {
        final boolean ignoreSys = (boolean) props.get("ignoreSys");
        final boolean ignoreDefault = (boolean) props.get("ignoreDefault");
        return repository.findByOwnerId(ownerId).stream()
                .filter(x -> !"sys".equals(x.getOwner().getUsername()) || !ignoreSys)
                .filter(x -> x.getDefault() == null || !x.getDefault() || !ignoreDefault)
                .toList();
    }

    public Optional<Repository> findByRootFullName(String rootFullName)
    {
        return repository.findByRootFullName(rootFullName);
    }

    Repository getRepositoryByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("Tag is not found"); });
    }

    public Page<Repository> findAllByFilter(Repository filter, Pageable pageable, Map<String, Object> props) {
        assignAccessTypes(filter);
        assignTags(filter);

        final boolean ignoreSys = (boolean) props.get("ignoreSys");
        final boolean ignoreDefault = (boolean) props.get("ignoreDefault");

        final ExampleMatcher.GenericPropertyMatcher containing =
                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<Repository> repositoryExample = Example.of(filter, ExampleMatcher.matching()
                .withMatcher("displayPath", containing)
                .withMatcher("displayName", containing)
                .withMatcher("displayFullName", containing)
                .withIgnoreNullValues());

        List<Repository> list = repository.findAll(repositoryExample, pageable).stream()
                .filter(x -> !"sys".equals(x.getOwner().getUsername()) || !ignoreSys)
                .filter(x -> x.getDefault() == null || !x.getDefault() || !ignoreDefault)
                .filter(x -> filter.getAccessTypes() == null || x.getAccessTypes().containsAll(filter.getAccessTypes()))
                .filter(x -> filter.getComments() == null || x.getComments().containsAll(filter.getComments()))
                .filter(x -> filter.getSecureCodes() == null || x.getSecureCodes().containsAll(filter.getSecureCodes()))
                .filter(x -> filter.getTags() == null || x.getTags().containsAll(filter.getTags()))
                .filter(x -> filter.getFiles() == null || x.getFiles().containsAll(filter.getFiles()))
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }



    public List<Repository> getAll(Map<String, Object> props) {
        final boolean ignoreSys = (boolean) props.get("ignoreSys");
        final boolean ignoreDefault = (boolean) props.get("ignoreDefault");
        return this.repository.findAll().stream()
                .filter(x -> !"sys".equals(x.getOwner().getUsername()) || !ignoreSys)
                .filter(x -> x.getDefault() == null || !x.getDefault() || !ignoreDefault)
                .toList();
    }

    public List<Repository> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    public Optional<Repository> getById(Long id)
    {
        return repository.findById(id);
    }

    @Transactional
    public Repository update(Long id, Repository newRepository) {
        assignAccessTypes(newRepository);
        assignTags(newRepository);
        assignComments(newRepository);
        Repository repository = getRepositoryByIdOrThrow(id);
        repositoryMapper.transferEntityDto(newRepository, repository);
        return this.repository.save(repository);
    }

    @Transactional
    public void deleteById(Long id) {
        Repository found = getRepositoryByIdOrThrow(id);
        repository.deleteById(found.getId());
    }

    private Repository assignFiles(Repository repository) {
        final Set<File> files = repository.getFiles();
        if (files != null) {
            final Set<File> found = repository.getFiles().stream().map(x -> {
                if (x.getId() != null) {
                    return fileRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Repository is not found")
                    );
                }
                return x;
            }).collect(Collectors.toSet());
            repository.setFiles(new HashSet<>(found));
        }
        return repository;
    }

    private Repository assignTags(Repository repository) {
        final Set<Tag> tags = repository.getTags();
        if (tags != null && !tags.isEmpty()) {
            final Set<Tag> found = tags.stream().map(x -> {
                if (x.getName() != null) {
                    return tagService.ensure(x.getName()).getKey();
                }
                return x;
            }).collect(Collectors.toSet());
            repository.setTags(new HashSet<>(found));
        }
        return repository;
    }

    private Repository assignComments(Repository repository) {
        final Set<Comment> comments = repository.getComments();
        if (comments != null) {
            final Set<Comment> found = comments.stream().map(x -> {
                if (x.getId() != null) {
                    return commentRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Comment not found")
                    );
                }
                return x;
            }).collect(Collectors.toSet());
            repository.setComments(new HashSet<>(found));
        }
        return repository;
    }

    private Repository assignUser(Repository repository) {
        final User owner = repository.getOwner();
        if (owner != null) {
            User user = owner;
            if (owner.getId() != null) {
                user = userRepository.findById(owner.getId()).orElseThrow(() ->
                        new ResourceNotFoundException("Id of User is not found")
                );
            } else if (owner.getUsername() != null) {
                user = userRepository.findByUsername(owner.getUsername()).orElseThrow(() ->
                        new ResourceNotFoundException("Username of User is not found")
                );
            } else if (owner.getEmail() != null) {
                user = userRepository.findByEmail(owner.getEmail()).orElseThrow(() ->
                        new ResourceNotFoundException("Email of User is not found")
                );
            } else if (owner.getPhone_number() != null) {
                user = userRepository.findByPhoneNumber(owner.getPhone_number()).orElseThrow(() ->
                        new ResourceNotFoundException("Phone number of User is not found")
                );
            }
            if (user != null) {
                repository.setOwner(user);
            }
        }
        return repository;
    }

    private Repository assignAccessTypes(Repository repository) {
        final Set<AccessType> ats = repository.getAccessTypes();
        if (ats != null && !ats.isEmpty()) {
            final Set<AccessType> found = ats.stream().map(x -> {
                if (x.getName() != null) {
                    return accessTypeService.ensure(x.getName()).getKey();
                }
                return x;
            }).collect(Collectors.toSet());
            repository.setAccessTypes(new HashSet<>(found));
        }
        return repository;
    }

//    private List<Repository> removeSysIf(boolean isIgnoreSys, Iterable<Repository> iterable) {
//        List<Repository> list = new ArrayList<>();
//        if (isIgnoreSys)
//            iterable.forEach(x -> { if (x.getId() != 1L) list.add(x); } );
//        return list;
//    }
}
