package onlinepay.service;

import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;
import onlinepay.exception.InvalidBillException;
import onlinepay.model.Bill;

public interface BillService {
    Double checkFundsAccount(Account payAccount, Bill bill) throws InsufficientFundsException;

    Boolean checkFundsMoreThan(Account payAccount, Bill bill) throws InsufficientFundsException;

    Boolean checkBillNumbers(Bill bill) throws InvalidBillException;
}
