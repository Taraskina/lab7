package org.example.exceptions;

public class UserIsNotOwnerException extends Exception {
    public UserIsNotOwnerException(String message) {
        super(message);
    }
}
