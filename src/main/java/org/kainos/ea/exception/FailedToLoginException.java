package org.kainos.ea.exception;

public class FailedToLoginException extends Exception {
    @Override
    public String getMessage() {
        return "Failed to Login";
    }
}
