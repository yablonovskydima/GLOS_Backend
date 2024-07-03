package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.entityMappers.SecureCodeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.SecureCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SecureCodeService implements CrudService<SecureCode, Long>
{
    private final SecureCodeRepository repository;
    private final SecureCodeMapper mapper;

    @Autowired
    public SecureCodeService(SecureCodeRepository repository, SecureCodeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    SecureCode getSecureCodeByIdOrThrow(Long id)
    {
        collect();
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("Access type is not found"));
    }

    @Transactional
    @Override
    public SecureCode create(SecureCode secureCode) {
        collect();
        return repository.save(secureCode);
    }

    @Override
    public List<SecureCode> getAll() {
        collect();
        return repository.findAll();
    }

    public Page<SecureCode> getAllByFilter(SecureCode code, Pageable pageable) {
        collect();
        return repository.findAll(Example.of(code), pageable);
    }

    @Override
    public List<SecureCode> getAll(EntityFilter filter)
    {
        collect();
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<SecureCode> getById(Long id) {
        collect();
        return repository.findById(id);
    }

    public Optional<SecureCode> getByResourcePath(String resourcePath)
    {
        collect();
        return repository.findByResourcePath(resourcePath);
    }

    @Transactional
    @Override
    public SecureCode update(Long id, SecureCode newSecureCode) {
        SecureCode secureCode = getSecureCodeByIdOrThrow(id);
        mapper.transferEntityDto(newSecureCode, secureCode);
        return repository.save(secureCode);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        collect();
    }

    private void collect() {
        List<SecureCode> secureCodes = repository.findAll();
        List<SecureCode> secureCodes2 = secureCodes.stream()
                .filter(x -> x.getExpirationDate() != null)
                .filter(x -> {
                    LocalDateTime now = LocalDateTime.now();
                    return x.getExpirationDate().isBefore(now);
                }).toList();
        repository.deleteAll(secureCodes2);
    }
}
