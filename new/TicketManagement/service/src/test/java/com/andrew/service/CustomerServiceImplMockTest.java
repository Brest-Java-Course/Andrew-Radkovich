package com.andrew.service;

import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDao;
import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.andrew.service.CustomerDataFixture.getExistingCustomer;
import static com.andrew.service.CustomerDataFixture.getNewCustomer;
import static com.andrew.service.TicketDataFixture.getAllTickets;
import static com.andrew.service.TicketDataFixture.getTicketsOfExistingCustomer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by andrew on 30.11.14.
 */
public class CustomerServiceImplMockTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private TicketDao ticketDao;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void addCustomer() {

        Customer customer = getNewCustomer();
        when(customerDao.addCustomer(customer)).thenReturn(1L);
        when(customerDao.getCustomerByNumber(customer.getIdentificationNumber())).thenReturn(null);
        Long id = customerService.addCustomer(customer);
        verify(customerDao).addCustomer(customer);
        verify(customerDao).getCustomerByNumber(customer.getIdentificationNumber());
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void removeCustomer() {

        Customer customer = getExistingCustomer();
        List<Ticket> tickets = getTicketsOfExistingCustomer(customer.getCustomerId());

        when(ticketDao.getTicketsOfCustomer(customer.getCustomerId())).thenReturn(tickets);
        doNothing().when(ticketDao).updateTicketSetTakenFalse(anyLong());
        doNothing().when(customerDao).removeCustomer(anyLong());

        customerService.removeCustomer(customer.getCustomerId());
        verify(customerDao).removeCustomer(anyLong());
        verify(ticketDao, times(2)).updateTicketSetTakenFalse(anyLong());
        verify(ticketDao).getTicketsOfCustomer(customer.getCustomerId());
    }

    @Test
    public void updateCustomer() {

        Customer customer = getExistingCustomer();
        Customer newCustomer = customer;
        newCustomer.setName("New name");

        when(customerDao.getCustomerByNumber(customer.getIdentificationNumber())).thenReturn(customer);
        doNothing().when(customerDao).updateCustomer(customer);
        when(customerDao.getCustomerById(customer.getCustomerId())).thenReturn(newCustomer);

        customerService.updateCustomer(newCustomer);
        customerService.getCustomerById(newCustomer.getCustomerId());
        verify(customerDao).getCustomerByNumber(customer.getIdentificationNumber());
        verify(customerDao).updateCustomer(customer);
        verify(customerDao).getCustomerById(customer.getCustomerId());
    }
}
