package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.UserDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserServiceImpl service;

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

        when(userDao.createUser(user))
                .thenThrow(new BankingAppSystemException("Database exception"));

        assertThrows(BankingAppSystemException.class, () -> {
            service.createUser(user);
        });
    }

    @Test
    public void testCreateUserWhenDaoReturnsTrue() {
        User user = createValidUser();

        when(userDao.createUser(user))
                .thenReturn(true);

        service.createUser(user);

        verify(userDao, times(1))
            .createUser(user);
    }

    @Test
    public void testCreateUserWhenDaoReturnsFalse() {
        User user = createValidUser();

        when(userDao.createUser(user))
                .thenReturn(false);

        assertThrows(BankingAppSystemException.class, () -> {
            service.createUser(user);
        });
    }

}
