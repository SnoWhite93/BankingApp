package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

public class ScreenEmployeeOptions implements Screen {

    private static final Logger log = Logger.getLogger(ScreenEmployeeOptions.class);
    private Input input = InputScanner.getInstance();

    @Override
    public void showScreen() {
        while (true) {
            log.info("---------------------------------------");
            log.info("Welcome to BankAholic!");
            log.info("Select from the following options: ");
            log.info("1) View pending account requests");
            log.info("2) View customer bank accounts");
            log.info("3) View log of all transactions");
            log.info("0) Back to previous screen");

            String option = input.readLine();
            switch (option) {
                case "0":
                    return; //exit app
                case "1":
                  new ScreenLogIn()
                          .showScreen();
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
