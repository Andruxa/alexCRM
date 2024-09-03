package ru.kabzex.server.service;

import ru.kabzex.server.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;

public interface CommonService<E extends AbstractEntity> {

    E save(E entity);

    void saveAll(List<E> entities);

    List<E> getAll();

    void delete(E entity);

    void deleteById(UUID id);

    Long count();

    E get(UUID id);
}
