package org.example.commands;

import org.example.entity.Person;
import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class Info extends Command implements Callable {

    public Info() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "info";

    public static Info staticFactory(String[] args, String value) {
        Info inst = new Info();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.addMessage(String.format("В коллекции : %s, обновленной %s,хранится %d элементов", Person.class.getName(), collectionManager.lastUpdated != null ? collectionManager.lastUpdated : "never", collectionManager.getCollectionSize()));
        return resp;
    }
}
