package org.example.commands;

import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.responce.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class Save extends Command implements Callable {

    public Save() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "save";

    public static Save staticFactory(String[] args, String value) {
        Save inst = new Save();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.save();
        return resp;
    }
}
