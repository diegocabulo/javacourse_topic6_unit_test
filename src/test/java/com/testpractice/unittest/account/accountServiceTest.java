package com.testpractice.unittest.account;

import com.testpractice.unittest.exception.InvalidTargetFundsException;
import com.testpractice.unittest.service.AccountServiceImpl;
import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class accountServiceTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvalidFundExceptions(){
        Account originAccount = generateAccount("test1", "name1", 1234,
                0d,false, 5L, "testBank");
        Account destinationAccount = generateAccount("test2", "name2", 123,
                10000d, false, 6L, "testBank2");

        InsufficientFundsException ex = Assertions.assertThrows(InsufficientFundsException.class,
                ()->accountService.checkFundsAndAccount(originAccount, destinationAccount, 10000d));

        Assertions.assertEquals(AccountServiceImpl.NOT_ENOUGH_FUNDS,
                ex.getMessage());
    }

    @Test
    void testInvalidFundsWithDifferentBank(){
        Account originAccount = generateAccount("test1", "name1", 1234,
                12000d,false, 5L, "testBank");
        Account destinationAccount = generateAccount("test2", "name2", 123,
                10000d, false, 6L, "testBank2");

        InsufficientFundsException ex = Assertions.assertThrows(InsufficientFundsException.class,
                ()->accountService.checkFundsAndAccount(originAccount, destinationAccount, 10000d));

        Assertions.assertEquals(AccountServiceImpl.NOT_ENOUGH_FUNDS_DIFFERENT_BANk,
                ex.getMessage());
    }

    @Test
    void testValidFunds(){
        Account originAccount = generateAccount("test1", "name1", 1234,
                17000d,false, 5L, "testBank");
        Account destinationAccount = generateAccount("test2", "name2", 123,
                10000d, false, 6L, "testBank2");

        Boolean isSafeToTransfer = accountService.checkFundsAndAccount(originAccount, destinationAccount,
                10000d);

        assert isSafeToTransfer;

    }

    @Test
    void testCheckFundsCurrentAccount(){
        Account destinationAccount = generateAccount("test2", "name2", 123,
                40000d, true, 6L, "testBank2");

        InvalidTargetFundsException ex = Assertions.assertThrows(InvalidTargetFundsException.class,
                ()-> accountService.checkFundsCurrentAccount(destinationAccount,10000d));

        Assertions.assertEquals(AccountServiceImpl.CURRENT_ACCOUNT_HAVE_MORE_THAN,
                ex.getMessage());
    }

    @Test
    void testValidCheckFundsCurrentAccount(){
        Account destinationAccount = generateAccount("test2", "name2", 123,
                20000d, true, 6L, "testBank2");

        Boolean isSafeToTransfer = accountService.checkFundsCurrentAccount(destinationAccount,10000d);

        assert isSafeToTransfer;
    }

    @Test
    void testCheckTransferAmount(){
        Account destinationAccount = generateAccount("test2", "name2", 123,
                20000d, true, 6L, "testBank2");

        Double amountToTransfer = accountService.checkTransferAmountMoreThan(destinationAccount,1500000d);

        Assertions.assertEquals(amountToTransfer, destinationAccount.getAccountBalance());
    }

    private Account generateAccount(String firstName, String lastName, Integer documentId, Double accountBalance,
                                    Boolean isCurrentAccount, Long bankId, String bankName){
        return Account.builder().firstName(firstName).lastName(lastName)
                .documentId(documentId).accountBalance(accountBalance)
                .isCurrentAccount(isCurrentAccount).bankId(bankId).bankName(bankName).build();
    }
}
