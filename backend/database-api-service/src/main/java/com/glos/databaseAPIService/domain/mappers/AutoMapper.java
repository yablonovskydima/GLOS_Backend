package com.glos.databaseAPIService.domain.mappers;

import java.util.Collection;

/**
 * @author Mykola Melnyk
 */
public interface AutoMapper<E, D> {
    D toDto(E entity);
    D toDto(E entity, boolean hardSet);
    Collection<D> toDtoAll(Iterable<E> iterable);
    Collection<D> toDtoAll(Iterable<E> iterable, boolean hardSet);
    E toEntity(D dto);
    E toEntity(D dto, boolean hardSet);
    Collection<E> toEntityAll(Iterable<D> iterable);
    Collection<E> toEntityAll(Iterable<D> iterable, boolean hardSet);
    void autoCopyDto(D source, D destination, boolean hardSet);
    void autoCopyEntity(E source, E destination, boolean hardSet);
    void transferDtoEntity(D source, E destination);
    void transferDtoEntity(D source, E destination, boolean hardSet);
    void transferEntityDto(E source, D destination);
    void transferEntityDto(E source, D destination, boolean hardSet);

}
