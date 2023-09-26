package vn.funix.FX21678.asm03;

import vn.funix.FX21678.asm01.Asm01;
import vn.funix.FX21678.asm02.models.Account;
import vn.funix.FX21678.asm02.models.Customer;
import vn.funix.FX21678.asm03.models.*;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Asm03 {
    private static final int EXIT_COMMAND_CODE = 0;
    private static final int EXIT_ERROR_CODE = -1;
    private static final String CUSTOMER_ID = "040092110192";
    private static final String CUSTOMER_NAME = "Hồ Diên Vinh";
    public static final String AUTHOR = "FX21678";
    public static final String VERSION = "v3.0.0";
    private static final Scanner scanner = new Scanner(System.in);
    private static final DigitalBank activeBank = new DigitalBank();

    public static void main(String[] args) {
        // Thêm customer vào list Customer của DigitalBank
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        customer.setCustomerId(CUSTOMER_ID);
        activeBank.getCustomers().add(customer);

        while (true) {
            System.out.println("+-----------------+-------------------------------------+-----------------+");
            System.out.println("| NGÂN HÀNG SỐ | " + AUTHOR + "@" + VERSION + " ");
            System.out.println("+-----------------+-------------------------------------+-----------------+");
            System.out.println("   1. Thông tin khách hàng");
            System.out.println("   2. Thêm tài khoản ATM");
            System.out.println("   3. Thêm tài khoản Tín Dụng");
            System.out.println("   4. Rút tiền");
            System.out.println("   5. Lịch sử giao dịch");
            System.out.println("   0. Thoát");
            System.out.println("+-----------------+-------------------------------------+-----------------+");
            System.out.println("Mời lựa chọn chức năng : ");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    showCustomer();
                    break;
                case 2:
                    addAccountATM();
                    break;
                case 3:
                    addAccountLoan();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 0:
                    System.out.println("Thoát chương trình !");
                    return;
                default:
                    System.out.println("Vui lòng chọn từ 0 -> 5 để sử dụng chức năng.");

            }
        }
    }

    public static boolean isAccountNumberExists(String accountNumber){
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        List<Account> accounts = customer.getAccounts();
        if (accounts.stream().filter(items -> items.getAccountNumber().contains(accountNumber)).findFirst().isPresent()){
            return true;
        }
        return false;
    }
    public static boolean validateAccount(String accountNumber){
        if (accountNumber.length() == 6 && Asm01.checkInt(accountNumber)){
            return true;
        }
        return false;
    }

    //  Chức năng 1: Xem thông tin khách hàng
    public static void showCustomer() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        DigitalCustomer digitalCustomer = new DigitalCustomer();
        digitalCustomer.setAccounts(customer.getAccounts());
        digitalCustomer.setName(customer.getName());
        digitalCustomer.setCustomerId(customer.getCustomerId());
        if (customer != null) {
            System.out.println("Thông tin khách hàng :");
            digitalCustomer.displayInformation();
        }
    }
    // Chức năng 2: Thêm tài khoản ATM
    public static void addAccountATM() {
        scanner.nextLine();
        System.out.println("Nhập số tài khoản :");
        while (true) {
            String accountNumber = scanner.nextLine();
            if (!validateAccount(accountNumber) || isAccountNumberExists(accountNumber)) {
                System.out.println("Số tài khoản không hợp lệ hoặc đã tồn tại !");
                System.out.println("Vui lòng nhập lại STK :");
            } else {
                System.out.println("Nhập số dư tài khoản :");
                Account account = new SavingsAccount();
                account.setAccountNumber(accountNumber);
                while (true) {
                    double balance = scanner.nextDouble();
                    if (balance < 50000) {
                        System.out.println("Số dư phải lớn hơn 50000");
                        System.out.println("Vui lòng nhập lại :");
                    } else {
                        account.setBalance(balance);
                        break;
                    }
                }
                Customer customer = activeBank.getCustomers().stream().filter(items -> items.getCustomerId().equals(CUSTOMER_ID)).findFirst().get();
                customer.getAccounts().add(account);
                System.out.println("Thêm tài khoản ATM thành công !");
                return;
            }
        }
    }

    // Chức năng 2: Thêm tài khoản tín dụng
    public static void addAccountLoan() {
        scanner.nextLine();
        System.out.println("Nhập số tài khoản :");
        while (true) {
            String accountNumber = scanner.nextLine();
            if (!validateAccount(accountNumber) || isAccountNumberExists(accountNumber)) {
                System.out.println("Số tài khoản không hợp lệ hoặc đã tồn tại !");
                System.out.println("Vui lòng nhập lại STK :");
            } else {
                Account account = new LoansAccount(accountNumber);
                Customer customer = activeBank.getCustomers().stream().filter(items -> items.getCustomerId().equals(CUSTOMER_ID)).findFirst().get();
                customer.addAccount(account);
                System.out.println("Thêm tài khoản LOAN thành công !");
                return;
            }
        }
    }
    // Chức năng 4: Rút tiền
    public static void withdrawMoney() {
        scanner.nextLine();
        System.out.println("Nhập số tài khoản cần rút tiền :");
        while (true) {
            String accountNumber = scanner.nextLine();
            if (!validateAccount(accountNumber) || !isAccountNumberExists(accountNumber)) {
                System.out.println("Số tài khoản không hợp lệ hoặc không tồn tại !");
                System.out.println("Vui lòng nhập lại STK :");
            } else {
                Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
                Optional<Account> account = customer.getAccounts().stream().filter(items -> items.getAccountNumber().equals(accountNumber)).findFirst();
                    while (true) {
                        System.out.println("+-----------------+-----------------------------------+-----------------+");
                        System.out.println(" - Chọn 1 để thực hiên giao dịch");
                        System.out.println(" - Chọn 0 hoặc 'No' để thoát khỏi giao dịch ");
                        String choose = scanner.nextLine();
                        switch (choose) {
                            case "1":
                                System.out.println("Nhập số tiền cần rút :");
                                double amount = scanner.nextDouble();
                                scanner.nextLine();
                                Account acc = account.get();
                                if (acc instanceof SavingsAccount) {
                                    ((SavingsAccount) acc).withdraw(amount);
                                } else {
                                    ((LoansAccount) acc).withdraw(amount);
                                }
                                break;
                            case "0":
                            case "No":
                                System.out.println("Thoát khỏi giao dịch .");
                                return;
                     }
                 }
            }
        }
    }


    // Chức năng 5: Tra cứu lịch sử giao dịch
    public static void viewTransactionHistory(){
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        List<Account> accounts = customer.getAccounts();
        DigitalCustomer digitalCustomer = new DigitalCustomer();
        digitalCustomer.setAccounts(customer.getAccounts());
        digitalCustomer.setName(customer.getName());
        digitalCustomer.setCustomerId(customer.getCustomerId());

        System.out.println("Lịch sử giao dịch :");
        digitalCustomer.displayInformation();
        for (Account account : accounts){
            if (account instanceof SavingsAccount){
                SavingsAccount savingsAccount = (SavingsAccount) account;
                List<Transaction> transactions = savingsAccount.getTransactions();
                for (Transaction tr : transactions) {
                    if (tr.getStatus().equals("DONE"))
                        System.out.printf("[GD]%14s  |%18s  |  %29s\n", tr.getAccountNumber(), "-" +tr.getAmount(), tr.getTime());
                }
            }else {
                if (account instanceof LoansAccount){
                    LoansAccount loansAccount = (LoansAccount) account;
                    List<Transaction> transactions = loansAccount.getTransactions();
                    for (Transaction tr : transactions){
                        if (tr.getStatus().equals("DONE")){
                            System.out.printf("[GD]%14s  |%18s  |  %29s\n", tr.getAccountNumber(), tr.getAmount(), tr.getTime());
                        }
                    }
                }
            }
        }
    }
}
