package ru.kabzex.server.service;

import com.vaadin.flow.spring.security.AuthenticationContext;
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
import ru.kabzex.server.exception.SomeoneAlreadyModifiedEntityException;
import ru.kabzex.server.repository.EntityRepository;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractService<E extends AbstractEntity, R extends EntityRepository<E>>
        implements CommonService<E> {
    protected final R repository;
    protected final ModelMapper mapper;
    @Autowired
    protected AuthenticationContext authenticationContext;

    @Autowired
    protected AbstractService(R repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public E save(E entity) {
        var author = authenticationContext.getPrincipalName().orElse("UNKNOWN");
        var currentDT = LocalDateTime.now();
        if (entity.getId() != null && repository.existsById(entity.getId())) {
            var old = Optional.ofNullable(get(entity.getId()));
            if (old.isPresent()) {
                var oldEntity = old.get();
                oldEntity.setDeleteAuthor(author);
                oldEntity.setDeleteDate(currentDT);
                repository.save(oldEntity);
            } else {
                throw new SomeoneAlreadyModifiedEntityException("Данная запись была изменена в другом сеансе. Закройте окно и выполните действие заново");
            }
            entity.setId(null);
        }
        entity.setCreateAuthor(author);
        entity.setCreateDate(currentDT);
        return repository.save(entity);
    }

    @Transactional
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
            return save(entity);
        }
    }

    protected abstract E getEmptyEntity();

    private <D extends AbstractUpdatableDTO> E updateFromDto(D dto) {
        var previousVersion = get(dto.getId());
        E currentVersion = getEmptyEntity();
        mapper.map(dto, currentVersion);
        dto.updateEntity(previousVersion, currentVersion);
        return save(currentVersion);
    }

    @Override
    public List<E> getAll() {
        return repository.findAllByDeleteDateIsNull();
    }

    @Override
    @Transactional
    public void delete(E entity) {
        entity.setDeleteAuthor(authenticationContext.getPrincipalName().orElse("UNKNOWN"));
        entity.setDeleteDate(LocalDateTime.now());
        repository.save(entity);
    }

    @Transactional
    public void deleteById(UUID entityId) {
        var entity = repository.getReferenceById(entityId);
        entity.setDeleteDate(LocalDateTime.now());
        entity.setDeleteAuthor(authenticationContext.getPrincipalName().orElse("UNKNOWN"));
        repository.save(entity);
    }

    @Transactional
    public void softDeleteById(Set<UUID> entityIds) {
        entityIds.forEach(this::deleteById);
    }

    @Override
    public Long count() {
        return repository.countByDeleteDateIsNull();
    }

    @Override
    public void saveAll(List<E> entities) {
        entities.forEach(this::save);
    }

    @Override
    public E get(UUID id) {
        return repository.findByIdAndDeleteDateIsNull(id);
    }

    @Transactional
    public <D extends AbstractDTO> D getAndMap(UUID id, Class<D> dtoClass) {
        var entity = repository.findByIdAndDeleteDateIsNull(id);
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
        return (wd, cq, cb) -> cb.isNull(wd.get(AbstractEntity_.deleteDate));
    }
}
