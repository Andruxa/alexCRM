package ru.kabzex.server.service;

import ru.kabzex.server.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;

public interface CommonService<E extends AbstractEntity> {
    E saveIfNotExists(E entity);

    E save(E entity);

    void saveAll(List<E> entities);

    List<E> getAll();

    void delete(E entity);

    void softDelete(E entity);

    Long count();

    E get(UUID id);
}
