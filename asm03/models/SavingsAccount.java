package vn.funix.FX21678.asm03.models;

import vn.funix.FX21678.asm02.models.Account;
import vn.funix.FX21678.asm03.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account implements IReportService,IWithdraw{

    List<Transaction> transactions = new ArrayList<>();

    public SavingsAccount() {
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public SavingsAccount(String accountNumber, double balance){
        this.setAccountNumber(accountNumber);
        this.setBalance(balance);
    }
    public void addTransaction(double amount , String status){
        LocalDateTime localDateTime = LocalDateTime.now();
        Transaction transaction = new Transaction(getAccountNumber(),amount,localDateTime.toString(),status);
        transactions.add(transaction);
    }

    public String getTitle(){
        return "BIÊN LAI GIAO DỊCH SAVINGS";
    }

    private static final int SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    @Override
    public void log(double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf(" %49s%n", getTitle());
        System.out.printf("  Ngày G/D: %59s%n", Utils.formatDate());
        System.out.printf("  ATM ID: %61s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("  Số TK: %62s%n", this.getAccountNumber());
        System.out.printf("  Số Tiền: %60s%n", Utils.formatBalance(amount) + "đ");
        System.out.printf("  Số Dư: %62s%n", Utils.formatBalance(this.getBalance()) + "đ");
        System.out.printf("  Phí + VAT: %58s%n","0đ" );
        System.out.println(Utils.getDivider());
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)){
            setBalance(this.getBalance() - amount );
            addTransaction(amount , "DONE");
            log(amount);
            return true;
        }
        addTransaction(amount, "FAIL");
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        return ((amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW && !isPremium()) || isPremium()
                && this.getBalance() - amount >= 50000 && amount % 10000 == 0 && amount >= 50000);
     }
}
