package org.example.commands;

import org.example.commands.types.ElementArgumented;

public interface ElementArgumentable extends IFactory{
    ElementArgumented elFactory(String[] args);
}
