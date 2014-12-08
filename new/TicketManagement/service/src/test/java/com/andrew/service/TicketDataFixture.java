package com.andrew.service;

import com.andrew.ticket.Ticket;
import org.springframework.aop.target.LazyInitTargetSource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
public class TicketDataFixture {

    public static Ticket getNewTicket() {

        Ticket ticket = new Ticket();
        ticket.setTaken(Boolean.FALSE);
        ticket.setTitle("StarTrack");
        ticket.setLocation(13);
        ticket.setDate(Date.valueOf("2014-1-1"));
        ticket.setCost(1000);
        ticket.setCustomerId(null);
        return ticket;
    }

    public static Ticket getExistingTicket(Long ticketId) {

        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setTaken(Boolean.TRUE);
        ticket.setTitle("Prestige");
        ticket.setLocation(1);
        ticket.setDate(Date.valueOf("2014-1-2"));
        ticket.setCost(1000);
        ticket.setCustomerId(1L);
        return ticket;
    }

    public static List<Ticket> getAllTickets() {

        List<Ticket> tickets = new ArrayList<Ticket>(5);

        return tickets;
    }
}
