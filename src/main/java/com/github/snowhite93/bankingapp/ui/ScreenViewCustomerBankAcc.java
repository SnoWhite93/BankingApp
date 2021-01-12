package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountService;
import com.github.snowhite93.bankingapp.service.AccountServiceImpl;
import com.github.snowhite93.bankingapp.service.UserService;
import com.github.snowhite93.bankingapp.service.UserServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

public class ScreenViewCustomerBankAcc implements Screen {

    private static final Logger log = Logger.getLogger(ScreenViewCustomerBankAcc.class);

    private Input input = InputScanner.getInstance();
    private UserService userService = new UserServiceImpl();
    private AccountService accountService = new AccountServiceImpl();

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        log.info("All users in the system: ");

        List<User> getAllUsersList = userService.getAllUsersList();

        for (User user : getAllUsersList) {
            log.info("User name - " + user.getUserName() + " - " + user.getFirstName() + " " + user.getLastName());
        }

        log.info("Enter user name to show account: ");
        String userName = input.readLine();

        User userByUserName = userService.findUserByUserName(userName);
        if (userByUserName == null) {
            log.error("Not existing user name, please enter a valid user name");
            return;
        }

        List<Account> accountList = accountService.retrieveAllAccountsForUserId(userByUserName.getUserId());
        for (Account account : accountList) {
            log.info("Account ID: " + account.getAccountId() + " - " + "Balance: " + account.getBalance());
        }
    }
}
