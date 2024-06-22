package org.example.commands.utility;

import org.example.commands.IFactory;
import org.example.commands.types.ElementAndValueArgumented;
import org.example.commands.types.ElementArgumented;
import org.example.commands.types.NoArgumented;
import org.example.commands.types.ValueArgumented;

public enum CommandTypes {
    ELEMENT_AND_VALUE_ARGUMENTED((v, args) -> ElementAndValueArgumented.newInstance(v, args)),
    ELEMENT_ARGUMENTED((v, args) -> ElementArgumented.newInstance(v, args)),
    VALUE_ARGUMENTED((v, args) -> ValueArgumented.newInstance(v, args)),
    WITHOUT_ARGUMENTS((v, args) -> NoArgumented.newInstance(v, args));
    final IFactory factory;

    CommandTypes(IFactory f) {
        this.factory = f;
    }

    public IFactory getFactory() {
        return factory;
    }
}
