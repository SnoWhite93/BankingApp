package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    public void noSuchAccountTest() {
        BankingAppUserException thrown = assertThrows(BankingAppUserException.class, () -> {
            service.withdraw(222222, 453466, 23.56);
        });

        assertEquals("No such account: 453466", thrown.getMessage());
    }

    @Test
    public void withdrawFromSomeoneElsesAccountTest() {
        Account account = new Account();
        account.setUserId(11111);

        when(accountDAO.findAccountByAccId(111466))
                .thenReturn(account);

        BankingAppUserException thrown = assertThrows(BankingAppUserException.class, () -> {
            service.withdraw(222256, 111466, 88.55);
        });

        assertEquals("Cannot withdraw from someone else's account, use transaction instead.", thrown.getMessage());
    }

    @Test
    public void withdrawMoreThanABalance() {
        Account account = new Account();
        account.setUserId(222256);

        when(accountDAO.findAccountByAccId(111466))
                .thenReturn(account);

        BankingAppUserException thrown = assertThrows(BankingAppUserException.class, () -> {
            service.withdraw(222256, 111466, 88.55);
        });

        assertEquals("Cannot withdraw more than current balance", thrown.getMessage());
    }

    @Test
    public void withdrawFailedBecauseDataBaseError() {
        when(accountDAO.findAccountByAccId(111466))
                .thenThrow(new BankingAppSystemException("Couldn't update balance"));

        BankingAppSystemException thrown = assertThrows(BankingAppSystemException.class, () -> {
            service.withdraw(222256, 111466, 88.55);
        });

        assertEquals("Couldn't update balance", thrown.getMessage());
    }

    @Test
    public void withdrawSuccess() {
        Account account = new Account();
        account.setUserId(222256);
        account.setBalance(208.54);

        when(accountDAO.findAccountByAccId(111466))
                .thenReturn(account);

        when(accountDAO.updateBalance(111466, 144.41))
                .thenReturn(true);

        service.withdraw(222256, 111466, 64.13);

        verify(accountDAO, times(1))
                .updateBalance(111466, 144.41);
    }

}
