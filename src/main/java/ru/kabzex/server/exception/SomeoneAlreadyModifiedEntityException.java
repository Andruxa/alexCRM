package ru.kabzex.server.exception;

public class SomeoneAlreadyModifiedEntityException extends RuntimeException {
    public SomeoneAlreadyModifiedEntityException(String message) {
        super(message);
    }
}
