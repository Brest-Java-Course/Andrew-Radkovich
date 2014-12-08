package com.andrew.service;

import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;
import static com.andrew.service.TicketDataFixture.*;

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
        expect(ticketDao.addTicket(ticket)).andReturn(1L);
        replay(ticketDao);
        ticketService.addTicket(ticket);
        verify(ticketDao);
    }
}
