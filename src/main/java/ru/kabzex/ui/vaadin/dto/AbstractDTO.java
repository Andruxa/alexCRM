package ru.kabzex.ui.vaadin.dto;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.utils.ClassUtils;

import java.util.UUID;

public abstract class AbstractDTO<E extends AbstractEntity> implements DTO {
    @Getter
    @Setter
    UUID id;

    public Class<E> getEntityClass() {
        return ClassUtils.getGenericParameterClass(this.getClass(), AbstractDTO.class, 0);
    }
}
