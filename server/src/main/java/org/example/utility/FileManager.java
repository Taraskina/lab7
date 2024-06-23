package org.example.utility;

import org.example.entity.Person;

import java.util.HashSet;

public interface FileManager {
    HashSet<Person> read(String fileName);
    void write(HashSet<Person> collection, String fileName);
}
