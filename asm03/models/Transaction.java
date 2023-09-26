package vn.funix.FX21678.asm03.models;

import vn.funix.FX21678.asm03.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public  class Transaction {
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private String status;

    public Transaction() {
    }

    public Transaction(String accountNumber, double amount, String time, String status) {
        this.id = UUID.randomUUID().toString();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = Utils.formatDateTransaction(time);
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return Utils.formatBalance(amount);
    }

    public void setAmount(double amount) {
        this.amount =  amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
