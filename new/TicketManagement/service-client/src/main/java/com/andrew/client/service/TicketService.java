package com.andrew.client.service;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
public interface TicketService {

    public void addTicket(Ticket ticket);
    public List<Ticket> selectNotTaken();
    public List<Ticket> selectNotTakenBetweenDates(String dateLow, String dateHigh);
    public List<Ticket> getTicketsOfCustomer(Long customerId);
    public Integer getTicketsSumOfCustomer(Long customerId);
    public void updateSetTakenTrue(Long ticketId, Long customerId);
}
