package vn.funix.FX21678.asm03.models;

import vn.funix.FX21678.asm02.models.Bank;
import vn.funix.FX21678.asm02.models.Customer;


import java.util.List;
import java.util.stream.Collectors;

public class DigitalBank extends Bank {

    public Customer getCustomerById(String cccd){
       Customer customer = super.getCustomers().stream().filter(cus -> cus.getCustomerId().equals(cccd)).findFirst().get();
        return customer;
    }
    public void addCustomer(String customerId, String name){
        List<String> listCCCD = super.getCustomers().stream().map(cus -> cus.getCustomerId()).collect(Collectors.toList());
        if (!listCCCD.contains(customerId)){
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setName(name);
            super.getCustomers().add(customer);
        }else {
            System.out.println("Tài khoản đã tồn tại !");
        }
    }

}
