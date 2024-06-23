package org.example.main;


import org.example.utility.FileManager;

import java.util.HashMap;

public class CommandManager {
    
    private final HashMap<String, Command> commands = new HashMap<>();
    private CollectionManager collectionManager;
    private FileManager fileManager;

    public CommandManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        //this.collectionManager.setFileManager(fileManager);
        for (String key : commands.keySet()) {
            commands.get(key).setCollectionManager(collectionManager);
            commands.get(key).setFileManager(fileManager);
        }
    }
    public void addCommands(Command... commands) {
        for (Command command : commands) {
            this.commands.put(command.getNameInConsole(), command);
            command.setCollectionManager(collectionManager);
            command.setFileManager(fileManager);
            command.setCommandManager(this);
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
