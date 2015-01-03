package com.andrew.client.service;

import com.andrew.customer.Customer;

import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
public interface CustomerService {

    public Long addCustomer(Customer customer);
    public void removeCustomer(Long customerId);
    public List<Customer> getCustomers();
    public Customer getCustomerById(Long customerId);
    public Customer getCustomerByNumber(String number);
    public void updateCustomer(Customer customer);
}
