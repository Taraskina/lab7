package org.example.utility;

import java.util.Scanner;

public class Context {

    private Scanner scanner;

    public Context(Scanner s){
        this.scanner = s;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
