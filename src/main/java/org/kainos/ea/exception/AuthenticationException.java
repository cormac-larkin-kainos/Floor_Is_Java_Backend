package org.kainos.ea.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String s) {
    }

    @Override
    public String getMessage() {
        return "Authentication failed, please enter valid credentials";
    };
}

