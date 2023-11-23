package org.kainos.ea.exception;

public class ProjectException extends Throwable {
    private final String message;

    public ProjectException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
