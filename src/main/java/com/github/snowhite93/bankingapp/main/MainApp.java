package com.github.snowhite93.bankingapp.main;

import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.ui.ScreenLoggedOut;
import org.apache.log4j.Logger;

import java.io.Console;
import java.util.Scanner;

public class MainApp {
   private static final Logger log = Logger.getLogger(MainApp.class);

    public static void main(String[] args) {
        ScreenLoggedOut loggedOut = new ScreenLoggedOut();
        loggedOut.showScreen();
    }

}
