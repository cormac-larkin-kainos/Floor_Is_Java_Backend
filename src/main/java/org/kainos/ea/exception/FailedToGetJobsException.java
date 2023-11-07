package org.kainos.ea.exception;

public class FailedToGetJobsException extends Exception {
    @Override
    public String getMessage() {
        return "A database error occurred, failed to get jobs";
    }
}
