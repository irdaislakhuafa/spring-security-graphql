package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, R> {
    public Optional<T> save(T entity);

    public Optional<T> update(T entity);

    public List<T> findAll();

    public T toEntity(R request);

    public List<T> toEntities(List<R> requests);
}
