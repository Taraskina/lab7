package org.example.exceptions;

public class IncorrectCommandUsing extends RuntimeException{
    public IncorrectCommandUsing(){
        super();
    }

    public IncorrectCommandUsing(String message){
        super(message);
    }
}
