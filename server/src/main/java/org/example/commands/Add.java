package org.example.commands;

import org.example.entity.Color;
import org.example.entity.Coordinates;
import org.example.entity.Location;
import org.example.entity.Person;
import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;
import static org.example.utility.CheckingReader.checkRead;

public class Add extends Command implements Callable {

    public Add() {
        super();
        this.commandType = CommandType.ELEMENT_ARGUMENTED;

    }

    public final String name = "add";

    public static Add staticFactory(String[] args, String value) {
        Add inst = new Add();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);


        collectionManager.add(
                new Person(
                        (String) checkRead("s", args[0]),
                        new Coordinates(
                                (Long) checkRead("l", args[1]),
                                (Long) checkRead("l", args[2])),
                        (double) checkRead("d", args[3]),
                        (Integer) checkRead("i", args[4]),
                        Color.choose(
                                (String) checkRead("s", args[6])),
                        new Location(
                                (long) checkRead("l", args[7]),
                                (Long) checkRead("l", args[8]),
                                (float) checkRead("f", args[9]))));

        collectionManager.sort();
        return resp;

    }
}
