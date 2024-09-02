package ru.kabzex.server.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.AbstractEntity_;
import ru.kabzex.server.exception.UpdateNotAllowedException;
import ru.kabzex.server.repository.EntityRepository;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public abstract class AbstractService<E extends AbstractEntity, R extends EntityRepository<E>>
        implements CommonService<E> {
    protected final R repository;
    protected final ModelMapper mapper;

    @Autowired
    protected AbstractService(R repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    public <D extends AbstractDTO> E saveFromDto(D dto) {
        if (dto.getId() != null) {
            if (dto instanceof AbstractUpdatableDTO updatableDTO) {
                return updateFromDto(updatableDTO);
            } else {
                throw new UpdateNotAllowedException("Обновление не предусмотрено");
            }
        } else {
            var entity = getEmptyEntity();
            mapper.map(dto, entity);
            return repository.save(entity);
        }
    }

    protected abstract E getEmptyEntity();

    private <D extends AbstractUpdatableDTO> E updateFromDto(D dto) {
        var previousVersion = get(dto.getId());
        E currentVersion = getEmptyEntity();
        mapper.map(dto, currentVersion);
        dto.updateEntity(previousVersion, currentVersion);
        return repository.save(currentVersion);
    }

    @Override
    public E saveIfNotExists(E entity) {
        return repository.existsById(entity.getId()) ? entity : repository.save(entity);
    }

    @Override
    public List<E> getAll() {
        return repository.findAllByDeletionDateIsNull();
    }

    @Override
    public void delete(E entity) {
        try {
            repository.delete(entity);
        } catch (Exception exception) {
            NotificationUtils.showError("Удаление невозможно! На данную запись есть ссылка в других таблицах.", exception);
        }
    }

    @Transactional
    public void softDelete(E entity) {
        entity.setDeletionDate(LocalDate.now());
        repository.save(entity);
    }

    @Transactional
    public void softDeleteById(Long entityId) {
        softDeleteById(entityId, LocalDate.now());
    }

    @Transactional
    public void softDeleteById(Long entityId, LocalDate deletionDate) {
        var entity = repository.getReferenceById(entityId);
        entity.setDeletionDate(deletionDate);
        repository.save(entity);
    }

    @Transactional
    public void softDeleteById(Set<Long> entityIds) {
        LocalDate now = LocalDate.now();
        entityIds.forEach(id -> softDeleteById(id, now));
    }

    @Override
    public Long count() {
        return repository.countByDeletionDateIsNull();
    }

    @Override
    public void saveAll(List<E> entities) {
        repository.saveAll(entities);
    }

    @Override
    public E get(Long id) {
        return repository.findByIdAndDeletionDateIsNull(id);
    }

    @Transactional
    public <D extends AbstractDTO> D getAndMap(Long id, Class<D> dtoClass) {
        var entity = repository.findByIdAndDeletionDateIsNull(id);
        return mapper.map(entity, dtoClass);
    }

    @Transactional
    public Page<E> getAllByFilter(DTOFilter filter, Pageable pageable) {
        return repository.findAll(whereExists().and(parseFilter(whereExists(), filter)), pageable);
    }

    @Transactional
    public List<E> findAllByFilter(DTOFilter filter) {
        return repository.findAll(whereExists().and(parseFilter(whereExists(), filter)));
    }

    @Transactional
    public Page<E> getAllByFilter(DTOFilter filter, int offset, int limit, Sort sort) {
        PageRequest page = PageRequest.of(offset / limit, limit, sort);
        return getAllByFilter(filter, page);
    }


    @Transactional
    public <D extends AbstractDTO> List<D> getAllByFilterAndMap(DTOFilter filter, Pageable pageable, Class<D> targetClass) {
        Page<E> page = getAllByFilter(filter, pageable);
        return page.stream()
                .map(element -> mapper.map(element, targetClass))
                .toList();
    }

    @Transactional
    public <D extends AbstractDTO> List<D> getAllByFilterAndMap(DTOFilter filter, int offset, int limit, Sort sort, Class<D> targetClass) {
        PageRequest page = PageRequest.of(offset / limit, limit, sort);
        return getAllByFilterAndMap(filter, page, targetClass);
    }

    @Transactional
    public <D extends AbstractDTO> List<D> getAllByFilterAndMap(DTOFilter filter, Class<D> targetClass) {
        var entities = repository.findAll(whereExists().and(parseFilter(whereExists(), filter)));
        return entities.stream()
                .map(element -> mapper.map(element, targetClass))
                .toList();
    }

    protected abstract Specification<E> parseFilter(Specification<E> specification, DTOFilter filter);

    public long countByFilter(DTOFilter filter) {
        return repository.count(parseFilter(whereExists(), filter));
    }

    protected Specification<E> whereExists() {
        return Specification.where(exists());
    }

    private Specification<E> exists() {
        return (wd, cq, cb) -> cb.isNull(wd.get(AbstractEntity_.deletionDate));
    }
}
