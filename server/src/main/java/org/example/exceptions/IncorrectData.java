package org.example.exceptions;

public class IncorrectData extends Error{
    public IncorrectData() {
        super();
    }

    public IncorrectData(String s) {
        super(s);
    }
}
