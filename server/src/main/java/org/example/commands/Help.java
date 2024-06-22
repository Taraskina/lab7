package org.example.commands;

import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utility.Callable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Help extends Command implements Callable {

    public Help() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "help";

    public static Help staticFactory(String[] args, String value) {
        Help inst = new Help();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.setMessages(Arrays.stream("help| справка/info| информация/show| элементы коллекции/add{element}| добавить элемент/update{id element}| обновить элемент по id/remove_by_id{id}| удалить элемент id/clear| очистить/save| сохранить/execute_script{filename}| исполнить команды из файла/exit| выйти/sum_of_weight| сумма значений поля weight для всех элементов/print_field_descending_hair_color| вывести color всех элементов".split("/")).collect(Collectors.toCollection(ArrayList::new)));
        return resp;
    }
}
