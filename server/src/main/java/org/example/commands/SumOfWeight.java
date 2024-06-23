package org.example.commands;

import org.example.entity.Person;
import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.responce.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class SumOfWeight extends Command implements Callable {

    public SumOfWeight() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }
    public final String name = "filter_greater_than_height";
    public static SumOfWeight staticFactory(String[] args,String value){
        SumOfWeight inst =  new SumOfWeight();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        int i = collectionManager.getCollectionStream().mapToInt(Person::getWeight).sum();
        resp.addMessage(Integer.toString(i));
        return resp;
    }
}
