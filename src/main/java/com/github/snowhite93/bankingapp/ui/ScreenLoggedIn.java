package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

public class ScreenLoggedIn implements Screen {

    private static final Logger log = Logger.getLogger(ScreenLoggedIn.class);
    private Input input = InputScanner.getInstance();

    @Override
    public void showScreen() {
        while (true) {
            log.info("---------------------------------------");
            log.info("Welcome to BankAholic!");
            log.info("Select from the following options: ");
            log.info("0) Log out ");

            String option = input.readLine();
            switch (option) {
                case "0":
                    return; //exit app
                default:
                    log.error("Invalid option: " + option);
                    break;
            }
        }
    }


}
