package ru.kabzex.server.exception;

public class UpdateNotAllowedException extends RuntimeException {
    public UpdateNotAllowedException(Throwable ex) {
        super(ex);
    }

    public UpdateNotAllowedException(String message, Throwable ex) {
        super(message, ex);
    }

    public UpdateNotAllowedException(String message) {
        super(message);
    }
}
