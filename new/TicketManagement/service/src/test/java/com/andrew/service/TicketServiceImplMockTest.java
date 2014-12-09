package com.andrew.service;

import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static com.andrew.service.TicketDataFixture.getAllTickets;
import static org.easymock.EasyMock.*;
import static com.andrew.service.TicketDataFixture.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by andrew on 30.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-services-mock-test.xml"})
public class TicketServiceImplMockTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketDao ticketDao;

    @After
    public void clean() {

        reset(ticketDao);
    }

    @Test
    public void addTicket() {

        Ticket ticket = getNewTicket();
        ticketDao.addTicket(ticket);
        expectLastCall().andReturn(1L);
        replay(ticketDao);
        Long id = ticketService.addTicket(ticket);
        verify(ticketDao);
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void removeTicket() {

        Ticket ticket = getExistingTicket(1L);
        ticketDao.removeTicket(ticket.getTicketId());
        expectLastCall();
        replay(ticketDao);
        ticketService.removeTicket(ticket.getTicketId());
        verify(ticketDao);
    }

    @Test
    public void getAllTicketsTest() {

        List<Ticket> allTickets = getAllTickets();
        expect(ticketDao.selectAllTickets()).andReturn(allTickets);
        replay(ticketDao);
        List<Ticket> tickets = ticketService.selectAllTickets();
        verify(ticketDao);
        assertFalse(tickets.isEmpty());
        assertEquals(tickets, allTickets);
    }

    @Test
    public void getTicketsByDate() {

        List<Ticket> allTickets = getAllTickets();
        expect(ticketDao.selectNotTakenByDate(Date.valueOf("2014-9-9"))).andReturn(allTickets);
        replay(ticketDao);
        List<Ticket> notTakenTicketsOfSpecificDate = ticketService.selectNotTakenByDate(Date.valueOf("2014-9-9"));
        verify(ticketDao);
        assertFalse(notTakenTicketsOfSpecificDate.isEmpty());
        assertEquals(allTickets, notTakenTicketsOfSpecificDate);
    }
}
