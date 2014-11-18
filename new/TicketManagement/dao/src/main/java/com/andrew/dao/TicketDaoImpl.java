package com.andrew.dao;

import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;

import java.util.List;

/**
 * Created by andrew on 18.11.14.
 */
public class TicketDaoImpl implements TicketDao{
    @Override
    public Long addTicket(Ticket ticket) {
        return null;
    }

    @Override
    public List<Customer> getCustomersByDateOfPerformance(String date) {
        return null;
    }

    @Override
    public Long getTicketsSumOfCustomer(Long customerId) {
        return null;
    }

    @Override
    public void updateTicket(Ticket ticket) {

    }

    @Override
    public void removeTicket(Long ticketId) {

    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {
        return null;
    }
}
