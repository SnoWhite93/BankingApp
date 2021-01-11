package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountRequestService;
import com.github.snowhite93.bankingapp.service.AccountRequestServiceImpl;
import org.apache.log4j.Logger;

import static com.github.snowhite93.bankingapp.ui.Inputs.readDouble;

public class ScreenApplyForAccStartingBalance implements Screen {

    private static final Logger log = Logger.getLogger(ScreenApplyForAccStartingBalance.class);

    private final User user;

    private Input input = InputScanner.getInstance();
    private AccountRequestService accountRequestService = new AccountRequestServiceImpl();

    public ScreenApplyForAccStartingBalance(User user) {
        this.user = user;
    }

    @Override
    public void showScreen() {

        log.info("-------------------");
        log.info("Creating new account request: ");


        log.info("Please enter the starting balance. It should have a minimun of $25 : ");
        double startingBalance = readDouble(input);

        try {
            accountRequestService.createRequest(user.getUserId(), startingBalance);
            log.info("Account request for user id " + user.getUserId() + " created!");
        } catch (BankingAppUserException e) {
            log.error(e.getMessage());
        }
    }

}
