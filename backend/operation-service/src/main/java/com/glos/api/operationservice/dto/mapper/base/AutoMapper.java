package com.glos.api.operationservice.dto.mapper.base;

import java.util.Collection;

public interface AutoMapper<E, D> {
    D toDto(E entity);
    Collection<D> toDtoAll(Iterable<E> iterable);
    E toEntity(D dto);
    Collection<E> toEntityAll(Iterable<D> iterable);
    void autoCopyDto(D source, D destination);
    void autoCopyEntity(E source, E destination);
    void transferDtoEntity(D source, E destination);
    void transferEntityDto(E source, D destination);
}
