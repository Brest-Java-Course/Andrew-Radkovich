package com.andrew.dao;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andrew on 29.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-dao-test.xml" })
public class TicketDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void selectAllTickets() {

        List<Ticket> tickets = ticketDao.selectAllTickets();
        LOGGER.debug("amount of tickets = {}", tickets.size());
        assertNotNull(tickets);
        assertFalse(tickets.isEmpty());
    }

    @Test
    public void selectNotTakenTickets() {

        List<Ticket> notTakenTickets = ticketDao.selectNotTaken();
        LOGGER.debug("amount of not taken tickets = {}", notTakenTickets.size());
        assertNotNull(notTakenTickets);
        assertFalse(notTakenTickets.isEmpty());
    }

    @Test
    public void addTicket() {

        Ticket ticket = new Ticket();
        ticket.setCustomerId(null);
        ticket.setCost(1234);
        ticket.setLocation(1);
        ticket.setTitle("V for Vendetta");
        ticket.setDate(Date.valueOf("2014-11-5"));
        ticket.setTaken(Boolean.FALSE);

        List<Ticket> allTickets = ticketDao.selectAllTickets();
        int sizebefore = allTickets.size();

        ticketDao.addTicket(ticket);
        allTickets = ticketDao.selectAllTickets();

        assertEquals(sizebefore, allTickets.size() - 1);
    }

    @Test
    public void selectNotTakenByDate() {

        List<Ticket> ticketsOfSpecificDate = ticketDao.selectNotTakenByDate(Date.valueOf("2014-3-15"));
        LOGGER.debug("amount of not taken tickets of specific date = {}", ticketsOfSpecificDate.size());//should be 4 here
        assertNotNull(ticketsOfSpecificDate);
        assertFalse(ticketsOfSpecificDate.isEmpty());
    }

    @Test
    public void selectNotTakenByDateAndTitle() {

        List<Ticket> ticketsOfSpecificDateAndTitle = ticketDao.selectNotTakenByDateAndTitle(Date.valueOf("2014-3-15"), "green mile");
        LOGGER.debug("amount of not taken tickets of specific date and title= {}", ticketsOfSpecificDateAndTitle.size());
        assertNotNull(ticketsOfSpecificDateAndTitle);
        assertFalse(ticketsOfSpecificDateAndTitle.isEmpty());
    }

    @Test
    public void selectNotTakenByTitle() {

        List<Ticket> ticketsOfSpecificTitle = ticketDao.selectNotTakenByTitle("green mile");
        LOGGER.debug("amount of not taken tickets of specific title= {}", ticketsOfSpecificTitle.size());
        assertNotNull(ticketsOfSpecificTitle);
        assertFalse(ticketsOfSpecificTitle.isEmpty());
    }

    @Test
    public void getTicketsOfCustomer() {

        List<Ticket> ticketsOfCustomer = ticketDao.getTicketsOfCustomer(2L);
        LOGGER.debug("amount of tickets of customer =  {}", ticketsOfCustomer);
        assertNotNull(ticketsOfCustomer);
        assertFalse(ticketsOfCustomer.isEmpty());
    }

    @Test
    public void removeTicket() {

        List<Ticket> tickets = ticketDao.selectAllTickets();
        int sizeBefore = tickets.size();

        ticketDao.removeTicket(9L);
        tickets = ticketDao.selectAllTickets();
        int sizeAfter = tickets.size();

        assertEquals(sizeAfter + 1, sizeBefore);
    }

    @Test
    public void updateTicket() {

        Ticket ticket = new Ticket();
        ticket.setCustomerId(null);
        ticket.setCost(2345);
        ticket.setLocation(1);
        ticket.setTitle("Pirates of the Caribbean");
        ticket.setDate(Date.valueOf("2014-11-1"));
        ticket.setTaken(Boolean.FALSE);

        Long id = ticketDao.addTicket(ticket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setTicketId(id);
        updatedTicket.setCustomerId(2L);
        updatedTicket.setCost(2345);
        updatedTicket.setLocation(1);
        updatedTicket.setTitle("Pirates of the Caribbean 2");
        updatedTicket.setDate(Date.valueOf("2014-11-1"));
        updatedTicket.setTaken(Boolean.FALSE);

        ticketDao.updateTicket(updatedTicket);

        ticket = ticketDao.selectTicketById(id);

        assertEquals(ticket, updatedTicket);
    }

    @Test
    public void updateSetTakenTrue() {

        List<Ticket> notTakenTickets = ticketDao.selectNotTaken();
        int notTakenTicketsSizeBefore = notTakenTickets.size();

        ticketDao.updateSetTakenTrue(notTakenTickets.get(0).getTicketId());
        assertEquals(notTakenTicketsSizeBefore, ticketDao.selectNotTaken().size() + 1);
    }

    @Test
    public void getCustomersByDate() {

        List<Customer> customers = ticketDao.getCustomersByDate(Date.valueOf("2014-3-1"));
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    public void getCustomersByDateAndNumber() {

        Customer customer = ticketDao.getCustomersByDateAndNumber(Date.valueOf("2014-3-2"), "AB2");
        assertNotNull(customer);
    }

    @Test
    public void getTicketsSumOfCustomer() {

        Integer ticketCost1 = 1000;
        Integer ticketCost2 = 2000;

        Customer customer = new Customer();
        customer.setIdentificationNumber("IN1");
        customer.setName("Van Halen");

        Long id = customerDao.addCustomer(customer);

        Ticket ticket1 = new Ticket();
        ticket1.setCustomerId(id);
        ticket1.setCost(ticketCost1);
        ticket1.setLocation(2);
        ticket1.setTitle("V for Vendetta");
        ticket1.setDate(Date.valueOf("2014-11-5"));
        ticket1.setTaken(Boolean.TRUE);

        Ticket ticket2 = new Ticket();
        ticket2.setCustomerId(id);
        ticket2.setCost(ticketCost2);
        ticket2.setLocation(3);
        ticket2.setTitle("V for Vendetta");
        ticket2.setDate(Date.valueOf("2014-11-5"));
        ticket2.setTaken(Boolean.TRUE);

        ticketDao.addTicket(ticket1);
        ticketDao.addTicket(ticket2);

        TotalCustomerCost customerCost = ticketDao.getTicketsSumOfCustomer(id);
        assertEquals((ticketCost1 + ticketCost2), customerCost.getTotalCost().longValue());
    }
}
