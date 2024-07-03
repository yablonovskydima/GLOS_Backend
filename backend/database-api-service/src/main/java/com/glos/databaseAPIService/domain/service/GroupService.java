package com.glos.databaseAPIService.domain.service;


import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entityMappers.GroupMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@Service
public class GroupService
{
    private final GroupRepository repository;
    private final GroupMapper mapper;

    @Autowired
    public GroupService(GroupRepository repository, GroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    Group getGroupByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("Access type is not found"));
    }

    @Transactional
    public Group create(Group group) {
        return repository.save(group);
    }

    public List<Group> getAll(boolean ignoreSys) {
        return removeSysIf(ignoreSys, repository.findAll());
    }

    public Page<Group> getPage(Pageable pageable, boolean ignoreSys) {
        List<Group> list = removeSysIf(ignoreSys, repository.findAll(pageable));
        return new PageImpl<>(list, pageable, list.size());
    }

    public Page<Group> getPageByFilter(Group group, Pageable pageable, boolean ignoreSys) {
        List<Group> list = removeSysIf(ignoreSys, repository.findAll(Example.of(group), pageable));
        return new PageImpl<>(list, pageable, list.size());
    }

    public List<Group> getAll(EntityFilter filter, boolean ignoreSys) {
        return removeSysIf(ignoreSys, repository.findAllByFilter(filter));
    }

    public List<Group> getAll(Group filter, boolean ignoreSys)
    {
        return removeSysIf(ignoreSys, repository.findAll(Example.of(filter)));
    }

    public Optional<Group> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Group update(Long id, Group newGroup) {
        Group group = getGroupByIdOrThrow(id);
        mapper.transferEntityDto(newGroup, group);
        return repository.save(group);
    }

    @Transactional
    public void deleteById(Long id) {
        getGroupByIdOrThrow(id);
        repository.deleteById(id);
    }

    private List<Group> removeSysIf(boolean isIgnoreSys, Iterable<Group> iterable) {
        List<Group> list = new ArrayList<>();
        if (isIgnoreSys)
            iterable.forEach(x -> { if (x.getId() != 1L) list.add(x); } );
        return list;
    }
}
