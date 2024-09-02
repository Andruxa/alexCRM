package ru.kabzex.ui.vaadin.dto;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractDTO implements DTO {
    @Getter
    @Setter
    Long id;
}
