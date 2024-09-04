package ru.kabzex.server.exception;

public class NoRoleExistsException extends RuntimeException {
    public NoRoleExistsException(String message) {
        super(message);
    }
}
