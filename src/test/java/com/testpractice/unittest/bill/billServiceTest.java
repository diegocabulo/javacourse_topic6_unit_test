package com.testpractice.unittest.bill;

import com.testpractice.unittest.model.Account;
import onlinepay.exception.InsufficientFundsException;
import onlinepay.exception.InvalidBillException;
import onlinepay.model.Bill;
import onlinepay.service.BillServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class billServiceTest {

    @InjectMocks
    private BillServiceImpl billService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnoughFundsAccount(){
        Account originAccount = generateAccount("test1", "name1", 1234,
                0d,false, 5L, "testBank");
        Bill bill = generateBill(1, "001234", 130000d, "10-03-2022");

        InsufficientFundsException ex = Assertions.assertThrows(InsufficientFundsException.class,
                ()->billService.checkFundsAccount(originAccount, bill));

        Assertions.assertEquals(BillServiceImpl.NOT_ENOUGH_FUNDS, ex.getMessage());
    }

    @Test
    void testDiscountBillAccountType(){
        Account originAccount = generateAccount("test2", "name2", 12345,
                1000000d,true, 6L, "testBank2");
        Bill bill = generateBill(2, "0012345", 120000d, "11-03-2022");

        Double amountToPayBill = billService.checkFundsAccount(originAccount, bill);

        Assertions.assertEquals(amountToPayBill, bill.getBillAmount());
    }

    @Test
    void testCheckFundsMoreThan(){
        Account originAccount = generateAccount("test2", "name2", 12345,
                100000d,true, 6L, "testBank2");
        Bill bill = generateBill(2, "0012345", 120000d, "11-03-2022");

        InsufficientFundsException ex = Assertions.assertThrows(InsufficientFundsException.class,
                ()->billService.checkFundsMoreThan(originAccount, bill));

        Assertions.assertEquals(BillServiceImpl.NOT_ENOUGH_FUNDS, ex.getMessage());
    }

    @Test
    void testValidCheckFundsMoreThan(){
        Account originAccount = generateAccount("test2", "name2", 12345,
                1000000d,true, 6L, "testBank2");
        Bill bill = generateBill(2, "0012345", 120000d, "11-03-2022");

        Boolean enoughFunds = billService.checkFundsMoreThan(originAccount, bill);

        assert enoughFunds;
    }

    @Test
    void testCheckBillNumbers(){
        Bill bill = generateBill(2, "012345", 120000d, "11-03-2022");

        InvalidBillException ex = Assertions.assertThrows(InvalidBillException.class,
                ()->billService.checkBillNumbers( bill));

        Assertions.assertEquals(BillServiceImpl.ERROR_BILL_ID_MESSAGE, ex.getMessage());
    }

    @Test
    void testValidCheckBillNumbers(){
        Bill bill = generateBill(2, "0012345", 120000d, "11-03-2022");

        Boolean isBillValid = billService.checkBillNumbers(bill);

        assert isBillValid;

    }

    private Account generateAccount(String firstName, String lastName, Integer documentId, Double accountBalance,
                                    Boolean isCurrentAccount, Long bankId, String bankName){
        return Account.builder().firstName(firstName).lastName(lastName)
                .documentId(documentId).accountBalance(accountBalance)
                .isCurrentAccount(isCurrentAccount).bankId(bankId).bankName(bankName).build();
    }

    private Bill generateBill(Integer companyId, String billId, Double billAmount, String dueDate){
        return Bill.builder().companyId(companyId).billId(billId).billAmount(billAmount).dueDate(dueDate).build();
    }
}
