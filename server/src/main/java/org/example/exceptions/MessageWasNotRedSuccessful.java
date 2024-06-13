package org.example.exceptions;

public class MessageWasNotRedSuccessful extends Exception{
    public MessageWasNotRedSuccessful() {
    }

    public MessageWasNotRedSuccessful(String message) {
        super(message);
    }
}
