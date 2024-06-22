package org.example.commands;

import org.example.commands.types.ElementAndValueArgumented;

@FunctionalInterface
public interface IFactory {
    ElementAndValueArgumented elFactory(String v, String[] args);
}
