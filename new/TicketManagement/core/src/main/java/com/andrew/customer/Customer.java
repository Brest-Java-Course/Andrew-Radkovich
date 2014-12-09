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

    @Override
    public boolean equals(Object obj) {

        if ( this == obj) {
            return true;
        }

        if ( !(obj instanceof Customer) ) {
            return false;
        }

        Customer customer = (Customer) obj;
        return customerId.equals(customer.customerId) &&
               name.equals(customer.name) &&
               identificationNumber.equals(customer.identificationNumber);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Customer{");
        sb.append("customerId=").append(customerId)
          .append(", name='").append(name).append('\'')
          .append(", identificationNumber='").append(identificationNumber)
          .append('\'').append('}');
        return sb.toString();
    }
}