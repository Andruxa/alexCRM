package ru.kabzex.server.exception;

public class EntityFormRequiredFieldsException extends Exception {
    public EntityFormRequiredFieldsException(String text) {
        super(text);
    }
}
