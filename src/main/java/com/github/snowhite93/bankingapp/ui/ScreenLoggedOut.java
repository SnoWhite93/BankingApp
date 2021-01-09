package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

public class ScreenLoggedOut implements Screen {

    Logger log = Logger.getLogger(ScreenLoggedOut.class);
    private Input input = new InputScanner();

    @Override
    public void showScreen() {
        while (true) {
            log.info("---------------------------------------");
            log.info("Welcome to BankAholic!");
            log.info("Select from the following options: ");
            log.info("1) Log In");
            log.info("2) Register");
            log.info("0) Exit Application ");

            String option = input.readLine();
            switch (option) {
                case "1":

                    break;
                case "2":
                    break;
                case "0":
                    return;
                default:
                    log.error("Invalid option: " + option);
                    break;


            }
        }
    }


}
