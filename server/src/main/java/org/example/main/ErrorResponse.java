package org.example.main;

public class ErrorResponse extends Response{
    public ErrorResponse(String message) {
        super("error", message);
    }
}
