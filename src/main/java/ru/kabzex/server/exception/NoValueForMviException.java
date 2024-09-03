package ru.kabzex.server.exception;

public class NoValueForMviException extends RuntimeException {
    public NoValueForMviException(String unknownEvent) {
        super(unknownEvent);
    }
}
