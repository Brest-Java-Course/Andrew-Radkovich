package com.andrew.TotalCost;

import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;

/**
 * Created by andrew on 22.11.14.
 */
public class TotalCustomerCost {

    private Customer customer;
    private Long totalCost;

    public TotalCustomerCost() {

    }

    public TotalCustomerCost(Customer customer, Long totalCost) {

        this.customer = customer;
        this.totalCost = totalCost;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public Long getTotalCost() {

        return totalCost;
    }

    public void setTotalCost(Long totalCost) {

        this.totalCost = totalCost;
    }
}