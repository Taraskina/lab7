package org.example.main;

import org.example.entity.Person;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.utility.ObjectConverter.toJson;

public class CollectionManager {
    private static CollectionManager manager = null;
    public Date lastUpdated;
    private HashSet<Person> collection;

    private CollectionManager() {
        this.collection = new HashSet<>();
    }

    public static CollectionManager getCollectionManager() {
        if (manager == null) {
            manager = new CollectionManager();
        }
        return manager;
    }

    public synchronized void add(Person person) {
        this.collection.add(person);
    }

    public synchronized Stream<Person> getCollectionStream() {
        return collection.stream();
    }

    public void clear() {
        collection.clear();
    }

    public void sort() {
        this.collection = collection.stream().sorted().collect(Collectors.toCollection(HashSet::new));
    }

    public void removeById(long id) {
        collection.removeIf(c -> c.getId() == id);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public void describe() {

    }

    public int getCollectionSize() {
        return collection.size();
    }

    public void save() {
        collection.forEach(person -> {
            String jsonString = toJson(person) + "\n";
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(App.jarFile.getParentFile(), App.fileName)))) {
                bos.write(jsonString.getBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}