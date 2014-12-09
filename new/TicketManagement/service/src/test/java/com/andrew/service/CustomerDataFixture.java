package com.andrew.service;

import com.andrew.customer.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
public class CustomerDataFixture {

    public static Customer getNewCustomer() {

        Customer customer = new Customer();
        customer.setIdentificationNumber("IdNum1");
        customer.setName("Rob Halford");
        return customer;
    }

    public static Customer getExistingCustomer() {

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setIdentificationNumber("IdNum2");
        customer.setName("Robert Plant");
        return customer;
    }

    public static List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<Customer>(3);

        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setIdentificationNumber("IdNum1");
        customer1.setName("Robert Plant");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setIdentificationNumber("IdNum2");
        customer2.setName("Jim Hendricks");

        Customer customer = new Customer();
        customer.setCustomerId(3L);
        customer.setIdentificationNumber("IdNum3");
        customer.setName("Cliff Burton");
        return customers;
    }
}
