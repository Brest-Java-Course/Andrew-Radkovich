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

    public static Ticket getExistingTicket() {

        Ticket ticket = new Ticket();
        ticket.setTicketId(1L);
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
        for(long i = 1; i <= 5; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketId(i);
            ticket.setCustomerId(i);
            ticket.setCost(1000);
            ticket.setTaken(Boolean.TRUE);
            ticket.setLocation(Long.valueOf(i).intValue());
            ticket.setDate(Date.valueOf("2014-9-9"));
            ticket.setTitle("The Dark Knight");
            tickets.add(ticket);
        }
        return tickets;
    }

    public static List<Ticket> getTicketsOfExistingCustomer(Long customerId) {

        int size = 2;
        List<Ticket> tickets = new ArrayList<Ticket>(size);
        for(int i = 1; i <= size; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketId(Long.valueOf(i));
            ticket.setCustomerId(customerId);
            ticket.setCost(1000);
            ticket.setTaken(Boolean.TRUE);
            ticket.setLocation(Long.valueOf(i).intValue());
            ticket.setDate(Date.valueOf("2014-1-1"));
            ticket.setTitle("The Dark Knight");
            tickets.add(ticket);
        }
        return tickets;
    }

    public static List<Ticket> selectTicketsWithSameDate(Date date) {

        int size = 5;
        List<Ticket> tickets = new ArrayList<Ticket>(size);
        for(int i = 0; i < size; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketId(Long.valueOf(i + 1));
            ticket.setCustomerId(1L);
            ticket.setCost(1000);
            ticket.setTaken(Boolean.TRUE);
            ticket.setLocation(Long.valueOf(i).intValue());
            ticket.setDate(date);
            ticket.setTitle("The Dark Knight");
            tickets.add(ticket);
        }
        return tickets;
    }
}
