package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.TransactionsDAO;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Transactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceImplTest {

    @Mock
    private TransactionsDAO transactionsDAO;
    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private TransactionsServiceImpl service;


    @Test
    public void approveTransactionWhenTransactionIsNull() {

        BankingAppUserException thrown = assertThrows(BankingAppUserException.class, () -> {
            service.approveTransaction(11, 232);
        } );

        assertEquals("No such transaction: 232", thrown.getMessage());
    }

    @Test
    public void whenTransactionNotPendingTest() {
        // (step 1) given -- prepare mocks
        Transactions transactions = new Transactions();
        transactions.setTransactionId(111222);

        when(transactionsDAO.findTransactionById(111222))
                .thenReturn(transactions);

        BankingAppUserException thrown = assertThrows(BankingAppUserException.class, () -> {
            service.approveTransaction(11233, 111222);
        });
        assertEquals("Transaction is not pending", thrown.getMessage());


    }

}
