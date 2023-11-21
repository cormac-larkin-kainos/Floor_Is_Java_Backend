package org.kainos.ea.exception;

public class FailedToGetJobBandsException extends Exception {

    @Override
    public String getMessage() {
        return "A database error occurred, failed to get job bands";
    }
}
