package ru.kabzex.server.exception;

public class CommonException extends RuntimeException {
    public CommonException(String errorMessage) {
        super(errorMessage);
    }
}
