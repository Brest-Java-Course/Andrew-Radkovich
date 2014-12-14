package com.andrew.service;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.dao.TicketDao;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrew on 30.11.14.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TicketDao ticketDao;

    public void setTicketDao(TicketDao ticketDao) {

        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public Long addTicket(Ticket ticket) {

        LOGGER.debug("add ticket={}", ticket);
        Assert.notNull(ticket);
        Assert.isNull(ticket.getTicketId());
        Assert.notNull(ticket.getLocation());
        Assert.notNull(ticket.getCost());
        Assert.notNull(ticket.getTitle());
        Assert.notNull(ticket.getDate());

        return ticketDao.addTicket(ticket);
    }

    @Override
    public void removeTicket(Long ticketId) {

        LOGGER.debug("remove ticket with id={}", ticketId);
        ticketDao.removeTicket(ticketId);
    }

    @Override
    public List<Ticket> selectNotTaken() {

        LOGGER.debug("select not taken tickets");
        return ticketDao.selectNotTaken();
    }

    @Override
    public List<Ticket> selectAllTickets() {

        LOGGER.debug("select all tickets");
        return ticketDao.selectAllTickets();
    }

    @Override
    public List<Ticket> selectNotTakenByDate(Date date) {

        LOGGER.debug("select not taken by date");
        return ticketDao.selectNotTakenByDate(date);
    }

    @Override
    public List<Ticket> selectNotTakenByDateAndTitle(Date date, String title) {

        LOGGER.debug("select not taken by date and title");
        return ticketDao.selectNotTakenByDateAndTitle(date, title);
    }

    @Override
    public List<Ticket> selectNotTakenByTitle(String title) {

        LOGGER.debug("select not taken by title");
        return ticketDao.selectNotTakenByTitle(title);
    }

    @Override
    public List<Customer> getCustomersByDate(Date date) {

        LOGGER.debug("select customers that bought tickets on specific date");
        return ticketDao.getCustomersByDate(date);
    }

    @Override
    public Customer getCustomersByDateAndNumber(Date date, String number) {

        LOGGER.debug("get Customer by Date({}) and number({})", date, number);
        return ticketDao.getCustomersByDateAndNumber(date, number);
    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {

        LOGGER.debug("select tickets of customer with id={}", customerId);
        return ticketDao.getTicketsOfCustomer(customerId);
    }

    @Override
    public TotalCustomerCost getTicketsSumOfCustomer(Long customerId) {

        LOGGER.debug("get total cost of customer with id={}", customerId);
        return ticketDao.getTicketsSumOfCustomer(customerId);
    }

    @Override
    public void updateTicket(Ticket ticket) {

        LOGGER.debug("update ticket={}", ticket);
        ticketDao.updateTicket(ticket);
    }

    @Override
    public void updateSetTakenTrue(Long ticketId) {

        LOGGER.debug("update ticket, set taken status, ticket_id={}", ticketId);
        ticketDao.updateSetTakenTrue(ticketId);
    }

    @Override
    public Ticket selectTicketById(Long ticketId) {

        LOGGER.debug("select ticket by id={}", ticketId);
        return ticketDao.selectTicketById(ticketId);
    }
}
