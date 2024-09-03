package ru.kabzex.ui.vaadin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class AbstractDTO implements DTO {
    @Getter
    @Setter
    UUID id;
}
