package com.glos.databaseAPIService.domain.service;


import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.entities.Role;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceAlreadyExistsException;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.GroupRepository;
import com.glos.databaseAPIService.domain.repository.RoleRepository;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 	@author - yablonovskydima
 */
@Service
public class UserService
{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            RoleRepository roleRepository,
            GroupRepository groupRepository
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    User getUserByIdOrThrow(Long id)
    {
        collect();
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("User is not found"); });
    }

    public Optional<User> findByUsername(String username)
    {
        collect();
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email)
    {
        collect();
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber)
    {
        collect();
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    public User create(User user) {
        collect();

        throwIfAlreadyExists(user);

        assignRoles(user);
        assignGroups(user);

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDateTime(now);
        user.setUpdatedDataTime(now);
        return userRepository.save(user);
    }

    public Page<User> getPage(Pageable pageable, boolean ignoreSys) {
        collect();
        List<User> list = removeSysIf(ignoreSys, userRepository.findAll(pageable));
        return new PageImpl<>(list, pageable, list.size());
    }

    public Page<User> getPageByFilter(User filter, Pageable pageable, boolean ignoreSys) {
        collect();
        final Example<User> userExample = Example.of(filter, ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatcher.of(
                        ExampleMatcher.StringMatcher.CONTAINING
                ))
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher.of(
                        ExampleMatcher.StringMatcher.CONTAINING
                ))
                .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatcher.of(
                        ExampleMatcher.StringMatcher.CONTAINING
                ))
                .withIgnoreNullValues());
        List<User> list = removeSysIf(ignoreSys, userRepository.findAll(userExample, pageable));
        return new PageImpl<>(list, pageable, list.size());
    }

    public List<User> getAll(boolean ignoreSys) {
        collect();
        List<User> users = removeSysIf(ignoreSys, userRepository.findAll());
        return users;
    }

    public List<User> getAll(User filter, boolean ignoreSys) {
        collect();

        final Example<User> userExample = Example.of(filter, ExampleMatcher.matching()
                        .withMatcher("username", ExampleMatcher.GenericPropertyMatcher.of(
                                ExampleMatcher.StringMatcher.CONTAINING
                        ))
                        .withMatcher("email", ExampleMatcher.GenericPropertyMatcher.of(
                                ExampleMatcher.StringMatcher.CONTAINING
                        ))
                        .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatcher.of(
                             ExampleMatcher.StringMatcher.CONTAINING
                        ))
                .withIgnoreNullValues());
        List<User> list = removeSysIf(ignoreSys, userRepository.findAll(userExample));
        return list.stream()
                .filter(x -> filter.getRoles() == null || x.getRoles().containsAll(filter.getRoles()))
                .filter(x -> filter.getGroups() == null || x.getGroups().containsAll(filter.getGroups()))
                .toList();
    }

    public List<User> getAll(EntityFilter filter) {
        collect();
        throw new UnsupportedOperationException();
    }

    public Optional<User> getById(Long id) {
        collect();
        return userRepository.findById(id);
    }

    @Transactional
    public User update(Long id, User newUser) {
        collect();
        User user = getUserByIdOrThrow(id);
        newUser.setId(null);
        boolean deleted = user.getIs_deleted();
        boolean isAccountNonLocked = user.getIs_account_non_locked();
        boolean isEnabled = user.getIs_enabled();
        userMapper.transferEntityDto(newUser, user);
        LocalDateTime now = LocalDateTime.now();
        user.setUpdatedDataTime(now);
        if (user.getIs_enabled() != isEnabled) {
            user.setDisabledDateTime((user.getIs_enabled()) ? null : now);
        }
        if (user.getIs_account_non_locked() != isAccountNonLocked) {
            user.setBlockedDateTime((user.getIs_account_non_locked()) ? null : now);
        }
        if (user.getIs_deleted() != deleted) {
            user.setDeletedDateTime((user.getIs_deleted()) ? user.getDeletedDateTime() : null);
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = getUserByIdOrThrow(id);
        delete(user);
    }

    @Transactional
    public void delete(User user)
    {
        userRepository.delete(user);
    }


    private void collect() {
        List<User> users = userRepository.findAll().stream()
                .filter(x -> x.getId() != 1L)
                .filter(x -> x.getIs_deleted() != null && x.getIs_deleted())
                .filter(x -> x.getDeletedDateTime() != null)
                .filter(x -> {
                    LocalDateTime now = LocalDateTime.now();
                    return x.getDeletedDateTime().isBefore(now);
                }).toList();
        userRepository.deleteAll(users);
    }

    private void throwIfAlreadyExists(User user) throws ResourceAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            Map.Entry<String, String> entry = Map.entry("username", user.getUsername());
            throw new ResourceAlreadyExistsException("Username '%s' is already taken.".formatted(user.getUsername()), entry);
        } else if (userRepository.findByUsername(user.getEmail()).isPresent()) {
            Map.Entry<String, String> entry = Map.entry("email", user.getEmail());
            throw new ResourceAlreadyExistsException("Email '%s' is already taken.".formatted(user.getEmail()), entry);
        } else if (userRepository.findByPhoneNumber(user.getPhone_number()).isPresent()) {
            Map.Entry<String, String> entry = Map.entry("phoneNumber", user.getPhone_number());
            throw new ResourceAlreadyExistsException("Phone number '%s' is already taken.".formatted(user.getPhone_number()), entry);
        }
    }

    private User assignRoles(User user) {
        final Set<Role> roles = user.getRoles();
        if (roles != null) {
            final Set<Role> found = roles.stream().map(x -> {
                if (x.getId() != null) {
                    return roleRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Role is not found")
                    );
                }
                return x;
            }).collect(Collectors.toSet());
            user.setRoles(new HashSet<>(found));
        }
        return user;
    }
    private User assignGroups(User user) {
        final Set<Group> groups = user.getGroups();
        if (groups != null) {
            final Set<Group> found = groups.stream().map(x -> {
                if (x.getId() != null) {
                    return groupRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Group is not found")
                    );
                }
                return x;
            }).collect(Collectors.toSet());
            user.setGroups(new HashSet<>(found));
        }
        return user;
    }

    private List<User> removeSysIf(boolean isIgnoreSys, Iterable<User> iterable) {
        List<User> list = new ArrayList<>();
        if (isIgnoreSys)
            iterable.forEach(x -> { if (x.getId() != 1L) list.add(x); } );
        return list;
    }
}
