package onlinepay.service;

import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;
import onlinepay.exception.InvalidBillException;
import onlinepay.model.Bill;

public class BillServiceImpl implements BillService{
    public static String NOT_ENOUGH_FUNDS = "No enough funds";
    public static String ERROR_BILL_ID_MESSAGE = "Invalid bill id";
    private static final Double DISCOUNT_PERCENTAGE = 0.90d;
    private static final Double MORE_PERCENTAGE_TO_PAY_BILL = 1.20d;

    @Override
    public Double checkFundsAccount(Account payAccount, Bill bill)throws InsufficientFundsException{
        if(payAccount.getAccountBalance() <= 0){
            throw new InsufficientFundsException(NOT_ENOUGH_FUNDS);
        }
        double billDiscount;
        if(payAccount.getIsCurrentAccount()){
            billDiscount = bill.getBillAmount() * DISCOUNT_PERCENTAGE;
            bill.setBillAmount(billDiscount);
        }
        return bill.getBillAmount();
    }

    @Override
    public Boolean checkFundsMoreThan(Account payAccount, Bill bill) throws InsufficientFundsException {
        if(payAccount.getAccountBalance() < MORE_PERCENTAGE_TO_PAY_BILL * bill.getBillAmount()){
            throw new InsufficientFundsException(NOT_ENOUGH_FUNDS);
        }
        return true;
    }

    @Override
    public Boolean checkBillNumbers(Bill bill) throws InvalidBillException {
        if(bill.getBillId().indexOf("00") != 0){
            throw new InvalidBillException(ERROR_BILL_ID_MESSAGE);
        }
        return true;
    }
}
