package com.andrew.service;

import com.andrew.dao.TicketDao;
import com.andrew.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static com.andrew.service.TicketDataFixture.getAllTickets;
import static com.andrew.service.TicketDataFixture.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by andrew on 30.11.14.
 */
public class TicketServiceImplMockTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketDao ticketDao;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTicket() {

        Ticket ticket = getNewTicket();

        when(ticketDao.addTicket(ticket)).thenReturn(1L);

        Long id = ticketService.addTicket(ticket);
        verify(ticketDao).addTicket(ticket);
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void removeTicket() {

        doNothing().when(ticketDao).removeTicket(anyLong());

        ticketService.removeTicket(anyLong());
        verify(ticketDao).removeTicket(anyLong());
    }

    @Test
    public void getAllTicketsTest() {

        List<Ticket> allTickets = getAllTickets();

        when(ticketDao.selectAllTickets()).thenReturn(allTickets);

        List<Ticket> tickets = ticketService.selectAllTickets();
        verify(ticketDao).selectAllTickets();

        assertFalse(tickets.isEmpty());
        assertEquals(tickets, allTickets);
    }

    @Test
    public void getNotTakenTicketsBetweenDates() {

        List<Ticket> allTickets = getAllTickets();
        Date dateLow = Date.valueOf("2014-3-1");
        Date dateHigh = Date.valueOf("2014-9-12");

        when(ticketDao.selectNotTakenBetweenDates(any(Date.class), any(Date.class))).thenReturn(allTickets);

        List<Ticket> notTakenTicketsOfBetweenDates = ticketService.selectNotTakenBetweenDates("2014-3-1", "2014-3-1");

        verify(ticketDao).selectNotTakenBetweenDates(any(Date.class), any(Date.class));

        assertFalse(notTakenTicketsOfBetweenDates.isEmpty());
        assertEquals(allTickets, notTakenTicketsOfBetweenDates);
    }

    @Test(expected = InvalidDateException.class)
    public void getNotTakenTicketsBetweenDatesThrowsException() {

        List<Ticket> expectedTickets = selectTicketsWithSameDate(Date.valueOf("2014-3-1"));
        when(ticketDao.selectNotTakenBetweenDates(any(Date.class), any(Date.class))).thenThrow(new InvalidDateException("Error parsing dates: ", "some dates"));
        //doThrow(new InvalidDateException());
        //when(ticketDao.selectNotTakenBetweenDates(any(Date.class), any(Date.class))).thenReturn(expectedTickets);

        List<Ticket> notTakenTicketsOfBetweenDates = ticketService.selectNotTakenBetweenDates("2014-3-1", "2014-9-121");

        verify(ticketDao).selectNotTakenBetweenDates(any(Date.class), any(Date.class));
    }

    @Test
    public void updateTicketsWhenCustomerRemovedMockTest() {

        doNothing().when(ticketDao).updateTicketsWhenCustomerRemoved(any(Long.class));

        ticketService.updateTicketsWhenCustomerRemoved(any(Long.class));

        verify(ticketDao).updateTicketsWhenCustomerRemoved(any(Long.class));
    }

    @Test
    public void checkTicketExistenceValidDateMockTest() {

        when(ticketDao.checkTicketExistence(Date.valueOf("2014-1-1"), "title", 1L)).thenReturn(Boolean.TRUE);

        Boolean exists = ticketService.checkTicketExistence("2014-1-1", "title", 1L);

        verify(ticketDao).checkTicketExistence(Date.valueOf("2014-1-1"), "title", 1L);

        assertEquals(Boolean.TRUE, exists);
    }

    @Test
    public void checkTicketNonexistenceValidDateTest() {

        when(ticketDao.checkTicketExistence(Date.valueOf("2014-1-1"), "title", 1L)).thenThrow(new EmptyResultDataAccessException(1));

        Boolean exists = ticketService.checkTicketExistence("2014-1-1", "title", 1L);

        verify(ticketDao).checkTicketExistence(Date.valueOf("2014-1-1"), "title", 1L);

        assertEquals(Boolean.FALSE, exists);
    }

    @Test
    public void countTicketsOfCustomerTest() {

        Long expectedResult = 2L;

        when(ticketDao.countTicketsOfCustomer(1L)).thenReturn(expectedResult);

        Long recievedResult = ticketService.countTicketsOfCustomer(1L);

        verify(ticketDao).countTicketsOfCustomer(1L);

        assertEquals(expectedResult, recievedResult);
    }
}
