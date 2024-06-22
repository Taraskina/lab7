package org.example.commands;

import org.example.commands.types.ValueArgumented;

public interface ValueArgumentable extends IFactory{
    ValueArgumented elFactory(String value);
}
