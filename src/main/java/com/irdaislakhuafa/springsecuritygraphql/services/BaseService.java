package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.*;

public interface BaseService<T, R> {
    public Optional<T> save(T entity);

    public Optional<T> update(T entity);

    public List<T> findAll();

    public T toEntity(R request) throws NoSuchElementException;

    public List<T> toEntities(List<R> requests) throws NoSuchElementException;
}
