package org.example.commands;

import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.responce.Response;
import org.example.utility.Callable;

import static org.example.main.App.collectionManager;

public class RemoveById extends Command implements Callable {

    public RemoveById() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }

    public final String name = "remove_by_id";
    public static RemoveById staticFactory(String[] args,String value){
        RemoveById inst =  new RemoveById();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        if (collectionManager.getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            collectionManager.removeById(Integer.parseInt(this.getValue()));
        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }
}
