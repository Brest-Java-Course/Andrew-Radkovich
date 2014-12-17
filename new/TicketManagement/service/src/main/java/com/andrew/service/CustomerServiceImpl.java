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

    public void setCustomerDao(CustomerDao customerDao) {

        this.customerDao = customerDao;
    }

    public void setTicketDao(TicketDao ticketDao) {

        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public Long addCustomer(Customer customer) {

        Assert.notNull(customer);
        Assert.isNull(customer.getCustomerId());
        Assert.notNull(customer.getName(), "customer's name should be specified");
        Assert.notNull(customer.getIdentificationNumber(), "customers PID should be specified");
        LOGGER.debug("SERVICE: add customer = {}", customer);

        Customer existingCustomer = getCustomerByNumber(customer.getIdentificationNumber());
        LOGGER.debug("SERVICE: get customer by number passed");
        if ( existingCustomer != null) {
            LOGGER.debug("Throws illegalArgumentException");
            throw new IllegalArgumentException(customer + " is present in DB");
        }
        LOGGER.debug("before customerDao added customer");
        return customerDao.addCustomer(customer);
    }

    @Override
    public void removeCustomer(Long customerId) {

        LOGGER.debug("SERVICE: remove customer with id = {}", customerId);
        List<Ticket> tickets = ticketDao.getTicketsOfCustomer(customerId);
        for(Ticket ticket : tickets) {
            LOGGER.debug("SERVICE: set NOT TAKEN to ticket with id={}", ticket.getTicketId());
            ticketDao.updateTicketSetTakenFalse(ticket.getTicketId());
        }
        customerDao.removeCustomer(customerId);
    }

    @Override
    public List<Customer> getCustomers() {

        LOGGER.debug("SERVICE: get customers");
        return customerDao.getCustomers();
    }

    @Override
    public Customer getCustomerById(Long customerId) {

        LOGGER.debug("SERVICE: get customer by id = {}", customerId);
        return customerDao.getCustomerById(customerId);
    }

    @Override
    public Customer getCustomerByNumber(String number) {

        LOGGER.debug("SERVICE: get customer by PID = {}", number);
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

        LOGGER.debug("SERVICE: update customer = {}", customer);
        customerDao.updateCustomer(customer);
    }
}
