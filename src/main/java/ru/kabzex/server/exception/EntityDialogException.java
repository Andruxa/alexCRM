package ru.kabzex.server.exception;

import com.vaadin.flow.data.binder.ValidationException;

public class EntityDialogException extends RuntimeException {
    public EntityDialogException(ValidationException ex) {
        super(ex);
    }
}
