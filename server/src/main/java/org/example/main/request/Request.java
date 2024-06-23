package org.example.main.request;

import org.example.main.Command;
import org.example.main.Message;
import org.example.main.User;

public class Request extends Message {
    public Command command;
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Request(String commandName) {

    }
    public Request(){}

    public Command getCommandToEx() {
        return command;
    }

    public void setCommandToEx(Command commandToEx) {
        this.command = commandToEx;
    }
}
