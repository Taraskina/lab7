package org.example.commands;

import org.example.commands.types.ElementAndValueArgumented;

public interface ElementAndValueArgumentable extends IFactory{
    ElementAndValueArgumented elFactory(String v, String[] args);
}
