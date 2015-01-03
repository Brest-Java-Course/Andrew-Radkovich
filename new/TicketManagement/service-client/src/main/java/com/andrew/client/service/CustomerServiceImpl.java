package com.andrew.client.service;

import com.andrew.client.rest.CustomerRestClient;
import com.andrew.client.rest.TicketRestClient;
import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by andrew on 2.1.15.
 */
public class CustomerServiceImpl implements CustomerService {

    public static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerRestClient customerRestClient;

    @Autowired
    private TicketRestClient ticketRestClient;

    public void setCustomerRestClient(CustomerRestClient customerRestClient) {

        this.customerRestClient = customerRestClient;
    }

    public void setTicketRestClient(TicketRestClient ticketRestClient) {

        this.ticketRestClient = ticketRestClient;
    }

    @Override
    public Long addCustomer(Customer customer) {

        Assert.notNull(customer);
        Assert.isNull(customer.getCustomerId());
        Assert.notNull(customer.getName(), "customer's name should be specified");
        Assert.notNull(customer.getIdentificationNumber(), "customers PID should be specified");
        LOGGER.debug("SERVICE: add customer = {}", customer);

        Customer existingCustomer = customerRestClient.getCustomerByNumber(customer.getIdentificationNumber());
        if(existingCustomer != null) {
            throw new IllegalArgumentException(customer + "is present in DB");
        }
        return customerRestClient.addCustomer(customer);
    }

    @Override
    public void removeCustomer(Long customerId) {

        ticketRestClient.updateTicketsWhenCustomerRemoved(customerId);
        customerRestClient.removeCustomer(customerId);
    }

    @Override
    public List<Customer> getCustomers() {

        return asList(customerRestClient.getAllCustomers());
    }

    @Override
    public Customer getCustomerById(Long customerId) {

        return customerRestClient.getCustomerById(customerId);
    }

    @Override
    public Customer getCustomerByNumber(String number) {

        return customerRestClient.getCustomerByNumber(number);
    }

    @Override
    public void updateCustomer(Customer customer) {

        Customer existingCustomer = customerRestClient.getCustomerByNumber(customer.getIdentificationNumber());
        if ( existingCustomer != null && existingCustomer.getCustomerId() != customer.getCustomerId()) {
            LOGGER.debug("Throws illegalArgumentException");
            throw new IllegalArgumentException(customer + " is present in DB");
        }
        customerRestClient.updateCustomer(customer);
    }
}
