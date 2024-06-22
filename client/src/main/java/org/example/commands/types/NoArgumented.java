package org.example.commands.types;

import org.example.commands.NoArgumentable;
import org.example.commands.utility.Command;

public class NoArgumented extends Command implements NoArgumentable {
    public NoArgumented(){
        super(null,null);
    }
    public static ElementAndValueArgumented newInstance(String v, String[] args){
        return new NoArgumented().elFactory(v,args);
    }


    @Override
    public NoArgumented elFactory() {
        return null;
    }

    @Override
    public ElementAndValueArgumented elFactory(String v, String[] args) {
        return new ElementAndValueArgumented(v, args);
    }
}
