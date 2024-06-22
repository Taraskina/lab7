package org.example.commands;

import org.example.entity.Person;
import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class PrintFieldDescendingHairColor extends Command implements Callable {

    public PrintFieldDescendingHairColor() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "print_field_descending_loyal";
    public static PrintFieldDescendingHairColor staticFactory(String[] args,String value){
        PrintFieldDescendingHairColor inst =  new PrintFieldDescendingHairColor();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        StringBuilder s = new StringBuilder();
        collectionManager.getCollectionStream().map(Person::getHairColor).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;

    }
}
