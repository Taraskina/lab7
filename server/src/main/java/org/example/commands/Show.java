package org.example.commands;

import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class Show extends Command implements Callable {

    public Show() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "show";

    public static Show staticFactory(String[] args, String value) {
        Show inst = new Show();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        if (collectionManager.isEmpty()) {
            resp.addMessage("В коллекции нет элементов");
        }
        collectionManager.getCollectionStream().forEach(
                w -> resp.addMessage(w + "\n"));
        return resp;
    }
}
