package ru.kabzex.server.exception;

public class EntityAlredyExists extends RuntimeException {
    public EntityAlredyExists(String message) {
        super(message);
    }
}
