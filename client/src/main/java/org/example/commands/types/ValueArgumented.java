package org.example.commands.types;

import org.example.commands.ValueArgumentable;
import org.example.commands.utility.Command;

public class ValueArgumented extends ElementAndValueArgumented implements ValueArgumentable {
    public ValueArgumented(String v) {

        super(v,null);
    }

    @Override
    public ElementAndValueArgumented elFactory(String v, String[] args) {
        return new ValueArgumented(v).elFactory(v,null);
    }

    public static Command newInstance(String v){
        return new ValueArgumented(v).elFactory(v,null);
    }


    public ValueArgumented elFactory(String v) {
        return new ValueArgumented(v);
    }

}
