package ru.kabzex.server.exception;

public class ReportCreateException extends RuntimeException {
    public ReportCreateException(Throwable ex) {
        super(ex);
    }

    public ReportCreateException(String message, Throwable ex) {
        super(message, ex);
    }

    public ReportCreateException(String message) {
        super(message);
    }
}
