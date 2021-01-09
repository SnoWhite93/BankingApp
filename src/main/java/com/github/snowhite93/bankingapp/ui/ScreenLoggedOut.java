package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

public class ScreenLoggedOut implements Screen {

    private static final Logger log = Logger.getLogger(ScreenLoggedOut.class);
    private Input input = InputScanner.getInstance();

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
                case "0":
                    return; //exit app
                case "1":

                    break;
                case "2":
                    new ScreenRegister()
                            .showScreen();
                    break;
                default:
                    log.error("Invalid option: " + option);
                    break;


            }
        }
    }


}
