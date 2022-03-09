package com.testpractice.unittest.service;

import com.testpractice.unittest.exception.InvalidTargetFundsException;
import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;

public interface AccountService {
    Boolean checkFundsAndAccount(Account originAccount, Account destinationAccount, Double transferAmount)
            throws InsufficientFundsException;

    Boolean checkFundsCurrentAccount(Account destinationAccount, Double transferAmount)
            throws InvalidTargetFundsException;

    Double checkTransferAmountMoreThan(Account destinationAccount, Double transferAmount);
}
