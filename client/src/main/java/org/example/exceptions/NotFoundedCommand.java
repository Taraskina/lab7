package org.example.exceptions;

public class NotFoundedCommand extends Error{
    public NotFoundedCommand() {
        super();
    }

    public NotFoundedCommand(String s) {
        super(s);
    }
}
