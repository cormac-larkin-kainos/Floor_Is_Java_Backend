package org.kainos.ea.exception;

public class FailedToGetCapabilitiesException extends Exception {

    @Override
    public String getMessage() {
        return "A database error occurred, failed to get capabilities";
    }
}
