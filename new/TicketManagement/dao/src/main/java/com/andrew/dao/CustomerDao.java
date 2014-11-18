package com.andrew.dao;

import com.andrew.customer.Customer;

import java.util.List;

/**
 * Created by andrew on 18.11.14.
 */
public interface CustomerDao {

    public Long addCustomer(Customer customer);
    public List<Customer> getCustomers();
    public void removeCustomer(Long id);
    public Customer getCustomerById(Long id);
    public Customer getCustomerByIN(String identificationNumber);
    public void updateCustomer(Customer customer);
}
