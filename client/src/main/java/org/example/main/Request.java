package org.example.main;

import org.example.commands.utility.Command;

public class Request extends Message {
    Command commandToExecute;

    public Command getCommandToExecute() {
        return commandToExecute;
    }

    public void setCommandToExecute(Command commandToExecute) {
        this.commandToExecute = commandToExecute;
    }
}
