package com.andrew.dao;

import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;

import java.util.List;

/**
 * Created by andrew on 18.11.14.
 */
public interface TicketDao {

    public Long addTicket(Ticket ticket);
    public List<Customer> getCustomersByDateOfPerformance(String date);
    public Long getTicketsSumOfCustomer(Long customerId);
    public void updateTicket(Ticket ticket);
    public void removeTicket(Long ticketId);
    public List<Ticket> getTicketsOfCustomer(Long customerId);
}
