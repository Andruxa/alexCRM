package ru.kabzex.server.exception;

public class NoHandlerException extends RuntimeException {
    public NoHandlerException(String unknownEvent) {
        super(unknownEvent);
    }
}
