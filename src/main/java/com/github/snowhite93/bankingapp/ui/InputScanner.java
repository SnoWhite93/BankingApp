package com.github.snowhite93.bankingapp.ui;

import java.util.Scanner;

public class InputScanner implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

}
