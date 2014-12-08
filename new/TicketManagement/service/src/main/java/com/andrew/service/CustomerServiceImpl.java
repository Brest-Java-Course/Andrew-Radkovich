package com.andrew.service;

import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDao;
import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    public static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    @Transactional
    public Long addCustomer(Customer customer) {

        LOGGER.debug("add customer = {}", customer);
        Assert.notNull(customer);
        Assert.isNull(customer.getCustomerId());
        Assert.notNull(customer.getName(), "customer's name should be specified");
        Assert.notNull(customer.getIdentificationNumber(), "customers PID should be specified");

        if ( customerDao.getCustomerByNumber(customer.getIdentificationNumber()) != null) {
            throw new IllegalArgumentException(customer + " is present in DB");
        }
        return customerDao.addCustomer(customer);
    }

    @Override
    public void removeCustomer(Long customerId) {

        LOGGER.debug("remove customer with id = {}", customerId);
        List<Ticket> tickets = ticketDao.getTicketsOfCustomer(customerId);
        for(Ticket ticket : tickets) {
            LOGGER.debug("set NOT TAKEN to ticket with id={}", ticket.getTicketId());
            ticketDao.updateTicketSetTakenFalse(ticket.getTicketId());
        }
        customerDao.removeCustomer(customerId);
    }

    @Override
    public List<Customer> getCustomers() {

        LOGGER.debug("get customers");
        return customerDao.getCustomers();
    }

    @Override
    public Customer getCustomerById(Long customerId) {

        LOGGER.debug("get customer by id = {}", customerId);
        return customerDao.getCustomerById(customerId);
    }

    @Override
    public Customer getCustomerByNumber(String number) {

        LOGGER.debug("get customer by PID = {}", number);
        Customer customer = null;
        try {
            customer = customerDao.getCustomerByNumber(number);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("empty result for customer with " + number);
        }
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {

        LOGGER.debug("update customer = {}", customer);
        customerDao.updateCustomer(customer);
    }
}
