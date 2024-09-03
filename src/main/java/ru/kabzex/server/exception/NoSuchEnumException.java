package ru.kabzex.server.exception;

public class NoSuchEnumException extends RuntimeException {
    public NoSuchEnumException(String errorMessage) {
        super(errorMessage);
    }
}
