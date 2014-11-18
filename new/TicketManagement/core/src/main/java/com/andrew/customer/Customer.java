package com.andrew.customer;

import com.andrew.ticket.Ticket;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by andrew on 15.11.14.
 */
public class Customer {

    private Long customerId;
    private String name;
    private String identificationNumber;

    public Customer() {

    }

    public Long getCustomerId() {

        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getIdentificationNumber() {

        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {

        this.identificationNumber = identificationNumber;
    }
}