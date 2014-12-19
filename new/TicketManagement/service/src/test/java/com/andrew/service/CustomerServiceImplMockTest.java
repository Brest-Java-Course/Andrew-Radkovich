package com.andrew.service;

import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDao;
import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.andrew.service.CustomerDataFixture.getExistingCustomer;
import static com.andrew.service.CustomerDataFixture.getNewCustomer;
import static com.andrew.service.TicketDataFixture.getAllTickets;
import static com.andrew.service.TicketDataFixture.getTicketsOfExistingCustomer;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by andrew on 30.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-services-mock-test.xml"})
public class CustomerServiceImplMockTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TicketDao ticketDao;

    @After
    public void clean() {

        reset(customerDao);
    }
    
    @Test
    public void addCustomer() {

        Customer customer = getNewCustomer();
        expect(customerDao.addCustomer(customer)).andReturn(1L);
        expect(customerDao.getCustomerByNumber(customer.getIdentificationNumber())).andReturn(null);
        replay(customerDao);
        Long id = customerService.addCustomer(customer);
        verify(customerDao);
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void removeCustomer() {

        Customer customer = getExistingCustomer();
        List<Ticket> tickets = getTicketsOfExistingCustomer(customer.getCustomerId());
        ticketDao.getTicketsOfCustomer(customer.getCustomerId());
        expectLastCall().andReturn(tickets);
        customerDao.removeCustomer(customer.getCustomerId());
        expectLastCall();
        replay(customerDao, ticketDao);
        customerService.removeCustomer(customer.getCustomerId());
        verify(customerDao, ticketDao);
    }

    @Test
    public void updateCustomer() {

        Customer customer = getExistingCustomer();
        Customer newCustomer = customer;
        newCustomer.setName("New name");

        customerDao.getCustomerByNumber(customer.getIdentificationNumber());
        expectLastCall().andReturn(customer);

        customerDao.updateCustomer(customer);
        expectLastCall();

        customerDao.getCustomerById(customer.getCustomerId());
        expectLastCall().andReturn(newCustomer);

        replay(customerDao);
        customerService.updateCustomer(newCustomer);
        customerService.getCustomerById(newCustomer.getCustomerId());
        verify(customerDao);
    }
}
