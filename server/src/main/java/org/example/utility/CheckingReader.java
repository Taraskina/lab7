package org.example.utility;

import org.example.exceptions.IncorrectCommandUsing;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public class CheckingReader {

    public static Object checkRead(String type, String input){
        Scanner scanner = new Scanner(input);
        Supplier<?> append;
        Object o = null;
        append = switch (type.toLowerCase()){
            case "b" -> scanner::nextBoolean;
            case "i" -> scanner::nextInt;
            case "l" -> scanner::nextLong;
            case "f" -> scanner::nextFloat;
            case "s" -> scanner::nextLine;
            default -> throw new IncorrectCommandUsing("Неверный тип данных");
        };
        try {
            return o = switch (type) {
                case "b" -> (Boolean) append.get();
                case "i" -> (Integer) append.get();
                case "l" -> (Long) append.get();
                case "f" -> (Float) append.get();
                case "s" -> (String) append.get();
                default -> o;
            };
        }catch (NoSuchElementException e){
            throw new IncorrectCommandUsing("Пропущен тип данных в CheckingReader");
        }
    }
}
