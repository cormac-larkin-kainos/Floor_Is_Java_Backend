package org.kainos.ea.exception;

/**
 * Thrown if a DAO fails to delete an object for any ready
 */
public class FailedtoDeleteException extends Throwable {
    public FailedtoDeleteException(String message){
        super(message);
    }
}
