package com.glos.accessservice.mappers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Mykola Melnyk
 */
public abstract class AbstractMapper<E, D>
        implements AutoMapper<E, D> {

    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    public AbstractMapper() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        this.entityClass = (Class<E>) actualTypeArguments[0];
        this.dtoClass = (Class<D>) actualTypeArguments[1];
        init();
    }

    protected void init() {

    }

    @Override
    public final D toDto(E entity) {
        return toDto(entity, false);
    }

    @Override
    public final D toDto(E entity, boolean hardSet) {
        try {
            doEntityNull(entity);
        } catch (NullPointerException e) {
            return null;
        }

        D dto;
        try {
            dto = doMakeDto();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating DTO instance", e);
        }

        transferEntityDto(entity, dto, hardSet);

        return dto;
    }

    @Override
    public final Collection<D> toDtoAll(Iterable<E> iterable) {
        return toDtoAll(iterable, false);
    }

    @Override
    public final Collection<D> toDtoAll(Iterable<E> iterable, boolean hardSet) {
        try {
            nonNullOrThrow(iterable);
        } catch (NullPointerException e) {
            return null;
        }
        List<D> dtos = new ArrayList<>();
        iterable.forEach(x -> dtos.add( toDto(x, hardSet) ));
        return dtos;
    }

    @Override
    public final E toEntity(D dto) {
        return toEntity(dto, false);
    }

    @Override
    public final E toEntity(D dto, boolean hardSet) {
        try {
            doDtoNull(dto);
        } catch (NullPointerException e) {
            return null;
        }

        E entity;
        try {
            entity = doMakeEntity();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating DTO instance", e);
        }

        transferDtoEntity(dto, entity, hardSet);

        return entity;
    }

    @Override
    public final Collection<E> toEntityAll(Iterable<D> iterable) {
        return toEntityAll(iterable, false);
    }

    @Override
    public final Collection<E> toEntityAll(Iterable<D> iterable, boolean hardSet) {
        try {
            nonNullOrThrow(iterable);
        } catch (NullPointerException e) {
            return null;
        }
        List<E> entities = new ArrayList<>();
        iterable.forEach(x -> entities.add( toEntity(x) ));
        return entities;
    }

    @Override
    public final void autoCopyDto(D source, D destination, boolean hardSet) {
        autoCopy(source, destination, hardSet);
    }

    @Override
    public final void autoCopyEntity(E source, E destination,boolean hardSet) {
        autoCopy(source, destination, hardSet);
    }

    @Override
    public void transferDtoEntity(D source, E destination) {
        transferDtoEntity(source, destination, false);
    }

    @Override
    public final void transferDtoEntity(D source, E destination, boolean hardSet) {
        preEntityCopy(source, destination);
        copyEntity(source, destination, hardSet);
        postEntityCopy(source, destination);
    }

    @Override
    public void transferEntityDto(E source, D destination) {
        transferEntityDto(source, destination, false);
    }

    @Override
    public final void transferEntityDto(E source, D destination, boolean hardSet) {
        preDtoCopy(source, destination);
        copyDto(source, destination, hardSet);
        postDtoCopy(source, destination);
    }



    protected void doDtoNull(D dto) throws NullPointerException  {
        nonNullOrThrow(dto);
    }

    protected void doEntityNull(E entity) throws NullPointerException  {
        nonNullOrThrow(entity);
    }

    protected D doMakeDto() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return dtoClass.getDeclaredConstructor().newInstance();
    }

    protected E doMakeEntity() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return entityClass.getDeclaredConstructor().newInstance();
    }

    protected void preDtoCopy(E source, D destination) {

    }

    protected void postDtoCopy(E source, D destination) {

    }

    protected void preEntityCopy(D source, E destination) {

    }

    protected void postEntityCopy(D source, E destination) {

    }

    protected void copyDto(E source, D destination, boolean hardSet) {
        autoCopy(source, destination, hardSet);
    }

    protected void copyEntity(D source, E destination, boolean hardSet) {
        autoCopy(source, destination, hardSet);
    }

    protected final <S, DS> void autoCopy(S source, DS destination, boolean hardSet) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] declaredFields = destination.getClass().getDeclaredFields();

        for(Field fieldDS : declaredFields) {
            fieldDS.setAccessible(true);
            for (Field fieldS : sourceFields) {
                fieldS.setAccessible(true);
                if (fieldS.getName().equals(fieldDS.getName())
                        && fieldS.getType().equals(fieldDS.getType())) {
                    try {
                        if (fieldS.get(source) != null || hardSet) {
                            fieldDS.set(destination, fieldS.get(source));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }
    }

    protected final void nonNullOrThrow(Object o) throws NullPointerException {
        Objects.requireNonNull(o);
    }
}

