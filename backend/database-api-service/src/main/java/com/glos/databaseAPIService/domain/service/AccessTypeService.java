package com.glos.databaseAPIService.domain.service;

import com.accesstools.AccessNode;
import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entityMappers.AccessTypeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.AccessTypeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@Service
public class AccessTypeService implements CrudService<AccessType, Long> {
    private final AccessTypeRepository accessTypeRepository;
    private final AccessTypeMapper accessTypeMapper;

    public AccessTypeService(
            AccessTypeRepository accessTypeRepository,
            AccessTypeMapper accessTypeMapper) {
        this.accessTypeRepository = accessTypeRepository;
        this.accessTypeMapper = accessTypeMapper;
    }

    @Transactional
    public Map.Entry<AccessType, Boolean> ensure(String name) {
        Optional<AccessType> accessTypeOpt = accessTypeRepository.findByName(name);
        AccessNode node = AccessNode.builder(name).build();
        AccessType accessType = accessTypeOpt.orElseGet(() -> {
            AccessType accessType1 = accessTypeRepository.save(
                    new AccessType(null, node.getPattern())
            );
            return accessType1;
        });
        return Map.entry(accessType, accessTypeOpt.isEmpty());
    }

    @Transactional
    @Override
    public AccessType create(AccessType accessType)
    {
        Objects.requireNonNull(accessType);
        if (accessType.getName() != null) {
            AccessNode node = AccessNode.builder(accessType.getName()).build();
            accessType.setName(node.getPattern());
        }
        return accessTypeRepository.save(accessType);
    }

    @Override
    public List<AccessType> getAll() {
        return accessTypeRepository.findAll();
    }

    @Override
    public List<AccessType> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<AccessType> getById(Long id) {
        return accessTypeRepository.findById(id);
    }


    public Optional<AccessType> getByName(String name) {
        return accessTypeRepository.findByName(name);
    }

    @Transactional
    @Override
    public AccessType update(Long id, AccessType e) {
        Objects.requireNonNull(e);
        if (e.getName() != null) {
            AccessNode node = AccessNode.builder(e.getName()).build();
            e.setName(node.getPattern());
        }
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeMapper.transferDtoEntity(e, found);
        return accessTypeRepository.save(found);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeRepository.deleteById(found.getId());
    }

    public Page<AccessType> getPageByFilter(AccessType accessType, Pageable pageable)
    {
        Objects.requireNonNull(accessType);
        if (accessType.getName() != null) {
            AccessNode node = AccessNode.builder(accessType.getName()).build();
            accessType.setName(node.getPattern());
        }
        return accessTypeRepository.findAll(Example.of(accessType), pageable);
    }
}
