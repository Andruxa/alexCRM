package ru.kabzex.ui.vaadin.dto;


import ru.kabzex.server.entity.AbstractEntity;

public abstract class AbstractUpdatableDTO<E extends AbstractEntity> extends AbstractDTO {

    public void updateEntity(E previousVersion, E currentVersion) {
        //in most cases nothing required
    }
}
