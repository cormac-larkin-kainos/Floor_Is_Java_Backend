package org.kainos.ea.exception;

public class InvalidCredentialsException extends Exception {
    @Override
    public String getMessage() {
        return "Login failed: invalid credentials";
    }
}
