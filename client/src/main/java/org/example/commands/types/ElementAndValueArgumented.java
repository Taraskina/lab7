package org.example.commands.types;

import org.example.commands.ElementAndValueArgumentable;
import org.example.commands.utility.Command;
import org.example.utility.Context;

import static org.example.utility.CheckingReader.readSomeArgs;

public class ElementAndValueArgumented extends Command implements ElementAndValueArgumentable {
    public String value;
    public ElementAndValueArgumented(String v, String[] args){
        super(v,args);
    }
    public ElementAndValueArgumented(Context ctx, String v){
        super(v,readSomeArgs(9,"s,l,f,l,b,f,s,s,s".split(","),ctx.getScanner(),(
                        "Введите имя;" +
                                "Введите целочисленную x-координату (x<=625));" +
                                "Введите y-координату в формате деcятичной дроби (y>=-354.0);" +
                                "Введите здоровье;" +
                                "Введите булевое значение true/false преданности;" +
                                "Введите десятичное число,характеризующее длинну;" +
                                """
                                                Введите одно из названия для оружия:
                                                    BOLT_PISTOL,
                                                    COMBI_PLASMA_GUN,
                                                    GRENADE_LAUNCHER,
                                                    INFERNO_PISTOL,
                                                    MULTI_MELTA;""" +
                                "Введите название главы;"+
                                "Введите название главы;").split(";"),
                "more length 0;less than 626;more than -353.0;more than 0;;;is weapon;more length 0;more length 0".split(";")));
    }


    @Override
    public ElementAndValueArgumented elFactory(String v, String[] args) {
        return new ElementAndValueArgumented(v,args);
    }

    public static ElementAndValueArgumented newInstance(String v,String[] args){
        return new ElementAndValueArgumented(v,args).elFactory(v,args);
    }
    public static ElementAndValueArgumented newInstance(Context ctx,String v){
        return new ElementAndValueArgumented(ctx,v)
                .elFactory(v,
                        readSomeArgs(9,"s,l,f,l,b,f,s,s,s".split(","),ctx.getScanner(),(
                                        "Введите имя;" +
                                                "Введите целочисленную x-координату (x<=625));" +
                                                "Введите y-координату в формате деcятичной дроби (y>=-354.0);" +
                                                "Введите здоровье;" +
                                                "Введите булевое значение true/false преданности;" +
                                                "Введите десятичное число,характеризующее длинну;" +
                                                """
                                                                Введите одно из названия для оружия:
                                                                    BOLT_PISTOL,
                                                                    COMBI_PLASMA_GUN,
                                                                    GRENADE_LAUNCHER,
                                                                    INFERNO_PISTOL,
                                                                    MULTI_MELTA;""" +
                                                "Введите название главы;"+
                                                "Введите название главы;").split(";"),
                                "more length 0;less than 626;more than -353.0;more than 0;;;is weapon;more length 0;more length 0".split(";")));
    }
}
