package org.example.utility;


import org.example.exceptions.IncorrectCommandUsing;

import java.util.*;
import java.util.function.Supplier;

public class CheckingReader {

    public static String[] readSomeArgs(int number,String[] types,Scanner input,String[] comments,String[] predicates){
        ArrayList<String> args = new ArrayList<>();
        for(int i = 0;i<number;i++){
            args.add(String.valueOf(checkyRead(types[i],predicates[i],comments[i],input)));

        }
        return  args.toArray(String[]::new);

    }


    public static Object checkyRead(String type,String predicate,String comment,Scanner sc)  {
        if (!comment.isEmpty()) {
            System.out.println(comment);
        }
        //System.out.printf("checkyRead(type = %s, predicate = %s,comment = %s)%n",type,predicate,comment);


        Supplier<?> append;


        Object o = null;
        append = switch (type.toLowerCase()) {
            case "b" -> sc::nextBoolean;
            case "i" -> sc::nextInt;
            case "l" -> sc::nextLong;
            case "f" -> sc::nextFloat;
            case "s" -> sc::nextLine;
            default -> throw new IncorrectCommandUsing("Неверный тип данных");

        };
        try {
            o = switch (type) {
                case "b" -> (Boolean) append.get();
                case "i" -> (Integer) append.get();
                case "l" -> (Long) append.get();
                case "f" -> (Float) append.get();
                case "s" -> (String) append.get();
                default -> o;
            };
            if (!predicate.isEmpty()) {
                if (proove(type, predicate, o)) {
                    return o;
                } else {
                    return checkyRead(type, predicate, ("Значение не подходит по условию,еще раз\n" + comment).replace("Значение не подходит по условию,еще раз\nЗначение не подходит по условию,еще раз\n", "Значение не подходит по условию    ,еще раз\n"), new Scanner(System.in));
                }
            } else {
                return o;
            }

        }catch(NoSuchElementException e){
            System.out.printf("checkyRead(type = %s, predicate = %s,comment = %s,input = %s)%n",type,predicate,comment,o);

            return checkyRead(type,predicate,("Вы ошиблись,еще раз\n"+comment).replace("Вы ошиблись,еще раз\nВы ошиблись,еще раз\n","Вы ошиблись,еще раз\n"),new Scanner(System.in));
        }

    }
    private static boolean proove(String type,String usl,Object o){

        String[] words = usl.split(" ");
        boolean right = true;
        if(usl.isEmpty()) return true;
        String pred0 = null;
        String pred1=null;
        String pred2=null;
        switch (words.length){
            case 1:
            {
                pred0 = words[0].strip();
                break;
            }
            case 2:
            {
                pred0 = words[0].strip();
                pred1 = words[1].strip();
                break;
            }
            case 3:
            {
                pred0 = words[0].strip();
                pred1 = words[1].strip();
                pred2 = words[2].strip();
                break;
            }


        }


        switch (type) {
            case "i", "l" -> {
                if (words.length == 3) {switch (pred0) {

                    case "more":
                        if (pred1.equals("than") && (new Scanner(pred2).hasNextInt() || new Scanner(pred2).hasNextLong())) {
                        if ((Long) o < new Scanner(pred2).nextLong()) {
                            right = false;
                        }
                    } else {
                        Arrays.stream(words).forEach(w->System.out.print(w+", "));
                        System.out.println();
                        throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead1");
                    }
                    break;
                    case "less":
                        if (pred1.equals("than") && (new Scanner(pred2).hasNextInt() || new Scanner(pred2).hasNextLong())) {
                        if ((Long) o > new Scanner(pred2).nextLong()) {
                            right = false;
                        }
                    } else {
                        Arrays.stream(words).forEach(w->System.out.print(w+", "));
                        System.out.println();
                        throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead2");
                    }
                    break;
                    default:
                        Arrays.stream(words).forEach(w->System.out.print(w+", "));
                        System.out.println();
                        throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead2");
                }} else {
                    Arrays.stream(words).forEach(w->System.out.print(w+", "));
                    System.out.println();
                    throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead3");}
            }
            case "f" -> {
                if (words.length == 3) {
                    switch (pred0) {
                        case "more":
                            if (pred1.equals("than") &&( (new Scanner(pred2).hasNextFloat()))||new Scanner(pred2).hasNextDouble()) {
                                if ((Float) o < new Scanner(pred2).nextFloat()) {
                                    right = false;}
                            } else {
                                System.out.printf("хуево вызвал проверку,proove(type,uslovie,obj),usl=%s, words = %n",usl);
                                Arrays.stream(words).forEach(w->System.out.print(w+", "));
                                System.out.println();
                                throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead4");
                            }
                            break;
                        case "less":
                            if (pred1.equals("than") && (new Scanner(pred2).hasNextFloat())) {
                                if ((Float) o >= new Scanner(pred2).nextFloat()) {
                                    right = false;}
                            } else {
                                Arrays.stream(words).forEach(w->System.out.print(w+", "));
                                System.out.println();
                                throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead5");
                            }
                            break;
                        default:
                            Arrays.stream(words).forEach(w->System.out.print(w+", "));
                            System.out.println();
                            throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead6");
                    }
                } else {
                    Arrays.stream(words).forEach(System.out::print);
                    System.out.println();
                    throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead7");}
            }
            case "s" -> {
                if (words.length == 3) {switch (pred0) {
                    case "more":
                        if (pred1.equals("length") && (new Scanner(pred2).hasNextInt())) {
                            if (((String) o).length() <= new Scanner(pred2).nextInt()) {
                                right = false;
                            }
                        } else {
                            Arrays.stream(words).forEach(w->System.out.print(w+", "));
                            System.out.println();
                            throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead8");
                        }
                        break;
                    case "less":
                        if (pred1.equals("length") && (new Scanner(pred2).hasNextInt())) {
                            if (((String) o).length() >= new Scanner(pred2).nextInt()) {
                                right = false;
                            }
                        } else {
                            Arrays.stream(words).forEach(w->System.out.print(w+", "));
                            System.out.println();
                            throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead9");
                        }
                        break;
                    default:
                        Arrays.stream(words).forEach(w->System.out.print(w+", "));
                        System.out.println();
                        throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead10");
                }
                } else if(words.length == 2){
                    if (pred0.equals("is")) {
                        if (Objects.equals(pred1, "weapon")) {
                            if (!o.equals("BOLT_PISTOL") && !o.equals("INFERNO_PISTOL") && !o.equals("MULTI_MELTA") && !o.equals("COMBI_PLASMA_GUN") && !o.equals("GRENADE_LAUNCHER")) {
                                right = false;
                            }
                        } else {
                            Arrays.stream(words).forEach(w -> System.out.print(w + ", "));
                            System.out.println();
                            throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead10");
                        }
                    }
                }else{
                    Arrays.stream(words).forEach(w->System.out.print(w+", "));
                    System.out.println();
                    throw new IncorrectCommandUsing("Syntax error in proove \n in checkyRead11");
                }
            }

        }
        return right;
    }

    public static Object checkyRead(String type,Scanner input)  {
        return  checkyRead(type,"","",input);
    }

    public  static Object checkyRead(String type,String comment,Scanner input)  {
        return  checkyRead(type,"",comment,input);
    }
}
