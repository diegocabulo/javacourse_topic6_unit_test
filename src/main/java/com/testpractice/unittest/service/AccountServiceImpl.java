package com.testpractice.unittest.service;

import com.testpractice.unittest.exception.InvalidTargetFundsException;
import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;

public class AccountServiceImpl implements AccountService {

    private static final Double TRANSFER_COST = 3500d;
    private static final Double TRANSFER_COST_MORE_THAN_1500 = 1500000d;
    private static final Double TRANSFER_COST_PERCENTAGE = 0.97d;
    public static String NOT_ENOUGH_FUNDS = "No enough funds";
    public static String NOT_ENOUGH_FUNDS_DIFFERENT_BANk = "No enough funds different bank";
    public static String CURRENT_ACCOUNT_HAVE_MORE_THAN = "you destination account has more than 3 times the amount you " +
            "want to transfer";

    @Override
    public Boolean checkFundsAndAccount(Account originAccount, Account destinationAccount, Double transferAmount)
            throws InsufficientFundsException {
        if(originAccount.getAccountBalance() <= 0){
            throw new InsufficientFundsException(NOT_ENOUGH_FUNDS);
        }
        if(!originAccount.getBankId().equals(destinationAccount.getBankId()) &&
                originAccount.getAccountBalance() < TRANSFER_COST + transferAmount){
            throw new InsufficientFundsException(NOT_ENOUGH_FUNDS_DIFFERENT_BANk);
        }
        return true;
    }

    @Override
    public Boolean checkFundsCurrentAccount(Account destinationAccount, Double transferAmount)
            throws InvalidTargetFundsException {
        if(destinationAccount.getIsCurrentAccount() && destinationAccount.getAccountBalance() > 3d * transferAmount){
            throw new  InvalidTargetFundsException(CURRENT_ACCOUNT_HAVE_MORE_THAN);
        }
        else {
            return true;
        }
    }

    @Override
    public Double checkTransferAmountMoreThan(Account destinationAccount, Double transferAmount) {
        double transferAmountResult;
        if(transferAmount >= TRANSFER_COST_MORE_THAN_1500){
            transferAmountResult = destinationAccount.getAccountBalance() +
                    transferAmount * TRANSFER_COST_PERCENTAGE;
        }else {
            transferAmountResult = destinationAccount.getAccountBalance() + transferAmount;
        }
        destinationAccount.setAccountBalance(transferAmountResult);
        return destinationAccount.getAccountBalance();
    }
}
