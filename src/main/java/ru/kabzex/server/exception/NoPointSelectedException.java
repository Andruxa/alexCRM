package ru.kabzex.server.exception;

public class NoPointSelectedException extends RuntimeException {
    public NoPointSelectedException(String unknownEvent) {
        super(unknownEvent);
    }
}
