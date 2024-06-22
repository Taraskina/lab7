package org.example.main;

public class Request extends Message {
    public Command command;

    public Command getCommandToEx() {
        return command;
    }

    public void setCommandToEx(Command commandToEx) {
        this.command = commandToEx;
    }
}
