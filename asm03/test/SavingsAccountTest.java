package vn.funix.FX21678.asm03.test;


import org.junit.jupiter.api.Test;
import vn.funix.FX21678.asm03.models.SavingsAccount;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SavingsAccountTest {

    @Test
    void withdraw(){
        SavingsAccount savingsAccount = new SavingsAccount("456789",30000000.00);
        boolean balance = savingsAccount.withdraw(5000000.00);
        assertTrue(balance);
    }

    @Test
    void isAccepted() {
        SavingsAccount savingsAccount = new SavingsAccount("456789",30000000.00);
        boolean balance = savingsAccount.withdraw(29900000.00);
        savingsAccount.isAccepted(29900000.00);
        assertTrue(balance);
    }
}