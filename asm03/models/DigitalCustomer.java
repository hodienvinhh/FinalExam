package vn.funix.FX21678.asm03.models;

import vn.funix.FX21678.asm02.models.Account;
import vn.funix.FX21678.asm02.models.Customer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DigitalCustomer extends Customer {

    public double getTotalAccountBalance() {
        double total = 0;
        for (Account account : this.getAccounts()) {
            total += account.getBalance();
        }
        return total;
    }

    @Override
    public void displayInformation() {
        Locale locale = new Locale("vi", "VI");
        String pattern = "###,###,###,###";
        DecimalFormat dcf = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        dcf.applyPattern(pattern);

        System.out.printf("%18s  |%18s  |%10s  |%18s\n", this.getCustomerId(), this.getName(), this.isPremium()?"Premium":"Normal", dcf.format(this.getTotalAccountBalance())+"đ");
        int i = 1;
        for (Account account : this.getAccounts()) {
            System.out.printf("%-3s%15s  |%18s  |%31s\n", i, account.getAccountNumber(),account instanceof SavingsAccount ? "SAVINGS":"LOANS",  dcf.format(account.getBalance())+"đ");
            i++;
        }
    }
    public boolean withdraw(String accountNumber, double amount) {
        List<String> accountNumbers = this.getAccounts().stream().map(item -> item.getAccountNumber()).collect(Collectors.toList());
        if (accountNumbers.contains(accountNumber)) {
            Account account = this.getAccounts().stream().filter(item -> item.getAccountNumber().equals(accountNumber)).findFirst().get();
            if (account instanceof SavingsAccount){
                ((SavingsAccount) account).withdraw(amount);
            }else {
                ((LoansAccount) account).withdraw(amount);
            }
            return true;
        }
        return false;
    }
    
}
