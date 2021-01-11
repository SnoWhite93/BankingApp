package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.UserService;
import com.github.snowhite93.bankingapp.service.UserServiceImpl;
import org.apache.log4j.Logger;

import static com.github.snowhite93.bankingapp.ui.Inputs.readDate;

public class ScreenMakeAWithdrawl implements Screen {

    private static final Logger log = Logger.getLogger(ScreenMakeAWithdrawl.class);

    private Input input = InputScanner.getInstance();
    private UserService userService = new UserServiceImpl();

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        log.info("Choose the account to withdraw money from: ");

        User user = new User();

        log.info("Choose a username:");
        user.setUserName(input.readLine());

        log.info("Choose a password:");
        user.setPassword(input.readLine());

        log.info("Enter first name:");
        user.setFirstName(input.readLine());

        log.info("Enter last name:");
        user.setLastName(input.readLine());

        log.info("Enter employment status:");
        user.setEmploymentStatus(input.readLine());

        log.info("Enter date of birth:");
        user.setDob(readDate(input));

        try {
            userService.createUser(user);
            log.info("User " + user.getUserName() + " created!");
        } catch (BankingAppException e) {
            log.error(e.getMessage(), e); //remove this when commiting cause it's asked
        }
    }


}
