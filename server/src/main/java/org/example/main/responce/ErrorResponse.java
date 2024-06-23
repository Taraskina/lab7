package org.example.main.responce;

public class ErrorResponse extends Response {
    public ErrorResponse(String message) {
        super("error", message);
    }
}
