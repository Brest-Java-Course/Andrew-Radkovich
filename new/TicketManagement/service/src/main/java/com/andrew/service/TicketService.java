package com.andrew.service;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
public interface TicketService {

    public Long addTicket(Ticket ticket);
    public void removeTicket(Long ticketId);
    public List<Ticket> selectNotTaken();
    public List<Ticket> selectAllTickets();
    public List<Ticket> selectNotTakenBetweenDates(String dateLow, String dateHigh);
    public List<Ticket> selectNotTakenByDateAndTitle(Date date, String title);
    public List<Ticket> selectNotTakenByTitle(String title);
    public List<Customer> getCustomersByDate(Date date);
    public Customer getCustomersByDateAndNumber(Date date, String number);
    public List<Ticket> getTicketsOfCustomer(Long customerId);
    public TotalCustomerCost getTicketsSumOfCustomer(Long customerId);
    public void updateTicket(Ticket ticket);
    public void updateSetTakenTrue(Long ticketId, Long customerId);
    public Ticket selectTicketById(Long ticketId);
}
