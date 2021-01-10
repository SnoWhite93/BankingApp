package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.AccountRequest;
import com.github.snowhite93.bankingapp.service.AccountRequestService;
import com.github.snowhite93.bankingapp.service.AccountRequestServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

public class ScreenPendingAccountRequests implements Screen {

    private static final Logger log = Logger.getLogger(ScreenPendingAccountRequests.class);

    private Input input = InputScanner.getInstance();
    private AccountRequestService accountRequestService = new AccountRequestServiceImpl();

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        log.info("All pending requests: ");
        List<AccountRequest> pendingAccRequestsList = accountRequestService.findAllPendingAccReqs();

        for (AccountRequest pendingAcc : pendingAccRequestsList) {
            log.info(pendingAcc.getRequestId() + " - user " + pendingAcc.getUserId() + " - starting balance " + pendingAcc.getStartingBalance());
        }
        log.info("Please choose from the following options: ");
        log.info("1)Approve request ");
        log.info("2)Reject request ");
        log.info("0)Go back to previous screen ");
        String option = input.readLine();
        switch (option) {
            case "0":
                return; //exit app
            case "1":
                break;
            case "2":
                break;
            default:
                log.error("Invalid option: " + option);
                break;

        }
    }

}
