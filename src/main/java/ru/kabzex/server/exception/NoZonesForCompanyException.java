package ru.kabzex.server.exception;

public class NoZonesForCompanyException extends RuntimeException {
    public NoZonesForCompanyException(String unknownEvent) {
        super(unknownEvent);
    }
}
