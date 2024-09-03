package ru.kabzex.server.exception;

public class InstrumentNotExistsException extends RuntimeException {
    public InstrumentNotExistsException(String unknownEvent) {
        super(unknownEvent);
    }
}
