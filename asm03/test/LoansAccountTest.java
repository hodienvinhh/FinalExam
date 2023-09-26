package vn.funix.FX21678.asm03.test;

import org.junit.jupiter.api.Test;
import vn.funix.FX21678.asm03.models.LoansAccount;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoansAccountTest {


    @Test
    public void withdraw() {
        LoansAccount loansAccount = new LoansAccount("123456");
        boolean balance = loansAccount.withdraw(20000000.00);
        assertTrue(balance);
    }

    @Test
    public void isAccepted_true() {
        LoansAccount loansAccount = new LoansAccount("123456");
        boolean balance = loansAccount.withdraw(20000000.00);
        loansAccount.isAccepted(20000000.00);
        assertTrue(balance);
    }
}