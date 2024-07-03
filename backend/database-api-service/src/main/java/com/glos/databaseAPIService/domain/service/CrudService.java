package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.filters.EntityFilter;

import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
public interface CrudService<T, ID> {

    T create(T e);

    List<T> getAll();

    List<T> getAll(EntityFilter filter);

    Optional<T> getById(ID id);

    T update(ID id, T e);

    void deleteById(ID id);

}
