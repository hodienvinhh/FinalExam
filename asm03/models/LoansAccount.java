package vn.funix.FX21678.asm03.models;

import vn.funix.FX21678.asm02.models.Account;
import vn.funix.FX21678.asm03.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoansAccount extends Account implements IReportService, IWithdraw {
    private static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    private static final double LOAN_ACCOUNT_MAX_BALANCE = 100000000;
    private List<Transaction> transactions = new ArrayList<>();

    private double overBalance; // Số dư

    public double getOverBalance() {
        return overBalance;
    }

    public void setOverBalance(double overBalance) {
        this.overBalance = overBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(double amount, String status ){
        LocalDateTime localDateTime = LocalDateTime.now();
        Transaction transaction = new Transaction(getAccountNumber(), amount, localDateTime.toString(), status);
        transactions.add(transaction);

    }


    public LoansAccount(String accountNumber){
        this.overBalance = LOAN_ACCOUNT_MAX_BALANCE;
        this.setAccountNumber(accountNumber);
    }

    public String getTitle() {
        return "BIÊN LAI GIAO DỊCH LOAN";
    }
    public double getFee(double amount){
        if (isPremium()){
            return amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE;
        }else {
            return amount * LOAN_ACCOUNT_WITHDRAW_FEE;
        }
    }

    @Override
    public void log(double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf("%49s%n", getTitle());
        System.out.printf(" Ngày G/D: %59s%n", Utils.formatDate());
        System.out.printf(" ATM ID: %61s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf(" Số TK: %62s%n", this.getAccountNumber());
        System.out.printf(" Số Tiền: %60s%n", Utils.formatBalance(amount));
        System.out.printf(" Số Dư: %62s%n", Utils.formatBalance(this.overBalance));
        System.out.printf(" Phí + VAT: %58s%n", Utils.formatBalance(getFee(amount)));
        System.out.println(Utils.getDivider());
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            setBalance(this.getBalance() + amount);
            addTransaction(amount, "DONE");
            overBalance = overBalance - amount - (getFee(amount)); // Số tiền còn lại sau khi rút
            log(amount);
            return true;
        }
        addTransaction(amount, "FAIL");
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        double total = amount  + (getFee(amount));
        if ((overBalance - total) >= 50000) {
            return true;
        }
        System.out.println("Số dư sau khi giao dịch phải lớn hơn 50000");
        return false;
    }
}
