package org.example.main;

import org.example.commands.NotFound;
import org.example.main.responce.Response;
import org.example.utility.Callable;
import org.example.utility.FileManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command implements Callable {
    private final String nameInConsole;
    public String[] args;
    String value;
    private String name = "command";
    protected FileManager fileManager;
    protected CommandManager commandManager;
    public CommandType commandType = null;
    protected CollectionManager collectionManager;

    public Command(String nameInConsole) {
        this.nameInConsole = nameInConsole;
    }

    public Response calling(String[] args, String v) {
        Response response = new Response();
        this.args = args;
        this.value = v;
        response.setSuccess(true);
        return response;
    }

    public Command castInto(Command name) {
        Command answer;
        try {
            answer = this.getClass().getConstructor().newInstance();
            answer.setArgs(name.getArgs());
            answer.setName(name.getName());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return answer;
    }

    public static Command extractCommand(String str) throws InvocationTargetException, IllegalAccessException {
        String[] tokens = str.split(" ");
        String prefix = "";
        for (int i = 0; i < tokens.length; i++) {
            prefix += tokens[i];
            if (Server.nameToHandleMap.containsKey(prefix)) {
                Method factory = Server.nameToHandleMap.get(prefix);
                if (i < tokens.length - 1) {

                    return (Command) factory.invoke(null, tokens[i + 1], null);

                } else {
                    return (Command) factory.invoke(null, null, null);
                }
            }
            prefix += " ";
        }
        return new NotFound();

    }

    public Command revalidate(String name) throws InvocationTargetException, IllegalAccessException {
        return extractCommand(name).castInto(this);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("***** ").append(this.getClass()).append(" Details *****\n");
        for (Field f : this.getClass().getFields()) {
            try {
                f.setAccessible(true);
                if (f.get(this) == null) {
                    s.append(f.getName()).append("=").append("null").append("\n");
                } else {
                    s.append(f.getName()).append("=").append(f.get(this).toString()).append("\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        s.append("*****************************");

        return s.toString();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String getNameInConsole() {
        return nameInConsole;
    }
}
