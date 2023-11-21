package org.kainos.ea.exception;

public class ProjectException extends Throwable {
    @Override
    public String getMessage(){
        return "Something went wrong!";
    }
}
