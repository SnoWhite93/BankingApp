package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.service.AccountRequestService;
import com.github.snowhite93.bankingapp.service.AccountRequestServiceImpl;
import org.apache.log4j.Logger;

import static com.github.snowhite93.bankingapp.ui.Inputs.readInt;

public class ScreenRejectAccountRequest implements Screen {

    private static final Logger log = Logger.getLogger(ScreenRejectAccountRequest.class);

    private Input input = InputScanner.getInstance();
    private AccountRequestService accountRequestService = new AccountRequestServiceImpl();

    @Override
    public void showScreen() {

        log.info("-------------------");
        log.info("Enter account request id that you want to reject:  ");
        int accountRequestId = readInt(input);
        log.info("Enter rejection reason: ");
        String rejectionReason = input.readLine();

        try {
            accountRequestService.rejectAccRequest(accountRequestId, rejectionReason);
            log.info("Account request id " + accountRequestId + " rejected because of " + rejectionReason);
        } catch (BankingAppUserException e) {
            log.error(e.getMessage(), e);
        }
    }

}
