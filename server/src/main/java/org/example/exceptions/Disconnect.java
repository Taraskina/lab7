package org.example.exceptions;

public class Disconnect extends Exception{
    public Disconnect(){
        super();
    }

    public Disconnect(String message){
        super(message);
    }
}
