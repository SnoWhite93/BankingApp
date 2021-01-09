package com.github.snowhite93.bankingapp.ui;

import java.util.Scanner;

public class InputScanner implements Input {

    private static final Input input = new InputScanner();
    private final Scanner scanner = new Scanner(System.in);

    private InputScanner() {
        // singleton
    }

    public static Input getInstance() {
        return input;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

}
