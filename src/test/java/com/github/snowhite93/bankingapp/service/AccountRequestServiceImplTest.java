package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.AccountRequestDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountRequestServiceImplTest {

    @Mock
    private AccountRequestDAO accountRequestDAO;

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountRequestServiceImpl service;

    @Test
    public void createRequestWhenInvalidStartingBalanceTest() {

        BankingAppException thrown = assertThrows(BankingAppException.class, () -> {
            service.createRequest(11233, 11);
        });

        assertEquals("Could not create request for user id 11233 because starting balance was less than $25.", thrown.getMessage());
    }

    @Test
    public void createRequestWhenValidStartingBalanceReturnsTrueTest() {
        // (step 1) given -- prepare mocks
        when(accountRequestDAO.createRequest(11233, 44))
                .thenReturn(true);

        // (step 2) when -- performing action
        service.createRequest(11233, 44);

        // (step 3) then -- verify action
        verify(accountRequestDAO, times(1))
                .createRequest(11233, 44);
    }

    @Test
    public void createRequestWhenDAOThrowExceptionTest() {
        when(accountRequestDAO.createRequest(11233, 44))
                .thenThrow(new BankingAppSystemException("Expecting this to be propagated"));

        BankingAppSystemException thrown = assertThrows(BankingAppSystemException.class, () -> {
            service.createRequest(11233, 44);
        });
        assertEquals("Expecting this to be propagated", thrown.getMessage());

    }


    @Test
    public void createRequestWhenDAOreturnsFalseTest() {
        when(accountRequestDAO.createRequest(11233, 44))
                .thenReturn(false);

        BankingAppSystemException thrown = assertThrows(BankingAppSystemException.class, () -> {
            service.createRequest(11233, 44);
        });
        assertEquals("Could not create request for user id 11233", thrown.getMessage());

    }

}

