package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.UserService;
import com.github.snowhite93.bankingapp.service.UserServiceImpl;
import org.apache.log4j.Logger;

public class ScreenLogIn implements Screen {

    private static final Logger log = Logger.getLogger(ScreenLogIn.class);

    private Input input = InputScanner.getInstance();
    private UserService userService = new UserServiceImpl();

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        log.info("Log in to your account");

        log.info("Enter your username:");
        String userName = input.readLine();

        log.info("Enter your password:");
        String password = input.readLine();

        try {
            User user = userService.findUserByUserNameAndPassword(userName, password);
            log.info("Logged in to BankAholic as " + userName);
            new ScreenLoggedIn()
                    .showScreen();
        } catch (BankingAppException e) {
            log.error(e.getMessage(), e); //remove this when commiting cause it's asked
        }
    }


}
