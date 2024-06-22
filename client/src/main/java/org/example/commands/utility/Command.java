package org.example.commands.utility;

import org.example.commands.Execute;
import org.example.commands.types.ElementAndValueArgumented;
import org.example.commands.types.ElementArgumented;
import org.example.commands.types.NoArgumented;
import org.example.commands.types.ValueArgumented;
import org.example.main.Request;
import org.example.utility.Context;

import java.lang.reflect.Field;
import java.util.Scanner;

public abstract class Command {
    private String name = "command";
    String value;
    String[] args;
    public Command(String v, String[] args) {
        this.args = args;
        this.value = v;
    }

    public Request calling(){
        Request request = new Request();
        request.setCommandToExecute(this);
        return request;
    }

    public static Command extractCommand(String str, Context ctx) {
        String[] tokens = str.split(" ");
        String prefix = "";
        for(int i = 0;i< tokens.length;i++){
            prefix+=tokens[i];
            if(CommandMapper.nameToTypeMap.containsKey(prefix)){
                CommandTypes type = CommandMapper.nameToTypeMap.get(prefix);
                return switch (type) {
                    case VALUE_ARGUMENTED -> {
                        if (i < tokens.length - 1) {
                            if (new Scanner(tokens[i + 1]).hasNextInt()) {
                                Command temp = new ValueArgumented(tokens[i + 1]);
                                temp.setName(prefix);

                                yield temp;
                            } else {
                                yield new NotFound();
                            }
                        } else yield new NotFound();

                    }
                    case WITHOUT_ARGUMENTS -> {
                        Command temp = new NoArgumented();
                        temp.setName(prefix);

                        yield temp;
                    }
                    case ELEMENT_ARGUMENTED -> {
                        Command temp = new ElementArgumented(ctx);
                        temp.setName(prefix);
                        yield temp;
                    }
                    case ELEMENT_AND_VALUE_ARGUMENTED -> {
                        if (i < tokens.length - 1) {

                            if (new Scanner(tokens[i + 1]).hasNextInt()) {
                                Command temp = new ElementAndValueArgumented(ctx, tokens[i + 1]);
                                i++;
                                temp.setName(prefix);
                                yield temp;
                            } else {
                                yield new NotFound();
                            }
                        } else yield new NotFound();
                    }
                };
            }else if (prefix.equals("execute_script")&&i< tokens.length-1){
                new Execute(tokens[i+1]).calling();
                i++;
            }
            prefix+=" ";
        }
        return new NotFound();

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
