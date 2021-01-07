package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.UserDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private static UserService service;
    private static UserDAO mock = mock(UserDAO.class);

    @BeforeAll
    public static void prepareTest() {
        mock = mock(UserDAO.class);
        service = new UserServiceImpl(mock);
    }

    private static User createValidUser() {
        Date dob = Date.valueOf(LocalDate.now().minusYears(20));

        User user = new User();
        user.setEmploymentStatus("unemployed");
        user.setEmployee(false);
        user.setLastName("BB");
        user.setFirstName("Billy");
        user.setPassword("hatecrew");
        user.setUserName("billyRockStar");
        user.setDob(dob);

        return user;
    }

    @BeforeEach
    public void resetMocks() {
        reset(mock);
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

    @Test
    public void testCreateUserWhenDatabaseError() {
        User user = createValidUser();

        when(mock.createUser(user))
                .thenThrow(new BankingAppSystemException("Database exception"));

        assertThrows(BankingAppSystemException.class, () -> {
            service.createUser(user);
        });
    }

    @Test
    public void testCreateUserWhenDaoReturnsTrue() {
        User user = createValidUser();

        when(mock.createUser(user))
                .thenReturn(true);

        service.createUser(user);
    }

    @Test
    public void testCreateUserWhenDaoReturnsFalse() {
        User user = createValidUser();

        when(mock.createUser(user))
                .thenReturn(false);

        assertThrows(BankingAppSystemException.class, () -> {
            service.createUser(user);
        });
    }

}
