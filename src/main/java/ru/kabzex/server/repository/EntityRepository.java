package ru.kabzex.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.kabzex.server.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface EntityRepository<E extends AbstractEntity> extends JpaRepository<E, UUID>, JpaSpecificationExecutor<E> {
    List<E> findAllByDeletionDateIsNull();

    E findByIdAndDeletionDateIsNull(UUID id);

    Long countByDeletionDateIsNull();

}
