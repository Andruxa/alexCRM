package ru.kabzex.ui.vaadin.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.utils.ClassUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractDTO<E extends AbstractEntity> implements DTO {
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private LocalDateTime deleteDate;
    private Long version;
    private String createdBy;
    private String modifiedBy;
    private String deletedBy;

    public Class<E> getEntityClass() {
        return ClassUtils.getGenericParameterClass(this.getClass(), AbstractDTO.class, 0);
    }
}
