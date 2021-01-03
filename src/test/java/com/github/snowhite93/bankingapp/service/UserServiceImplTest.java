package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {

    private static UserService service;

    @BeforeAll
    public static void prepareTest() {
        service = new UserServiceImpl();

    }

    @Test
    public void testFindUserByUserName() {

        assertThrows(BankingAppUserException.class, () -> {
            service.findUserByUserName(null);
        });
    }

    @Test
    public void testFindUWhenUNnull() {
        assertThrows(BankingAppUserException.class, () -> {
            service.findUserByUserNameAndPassword(null, "kk");
        });
    }

    @Test
    public void testFindUserWhenPassNull() {
        assertThrows(BankingAppUserException.class, () -> {
            service.findUserByUserNameAndPassword("vvv", null);
        });
    }

}