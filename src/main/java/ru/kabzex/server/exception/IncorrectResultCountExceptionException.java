package ru.kabzex.server.exception;

public class IncorrectResultCountExceptionException extends RuntimeException {
    public IncorrectResultCountExceptionException(Throwable ex) {
        super(ex);
    }

    public IncorrectResultCountExceptionException(String message, Throwable ex) {
        super(message, ex);
    }

    public IncorrectResultCountExceptionException(String message) {
        super(message);
    }
}
