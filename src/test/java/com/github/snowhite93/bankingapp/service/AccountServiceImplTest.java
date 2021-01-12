package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
