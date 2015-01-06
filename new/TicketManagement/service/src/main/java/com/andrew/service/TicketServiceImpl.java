package com.andrew.service;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.dao.TicketDao;
import com.andrew.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

import static com.andrew.service.DateValidator.isValidDate;

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

        LOGGER.debug("SERVICE: add ticket={}", ticket);
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

        LOGGER.debug("SERVICE: remove ticket with id={}", ticketId);
        ticketDao.removeTicket(ticketId);
    }

    @Override
    public List<Ticket> selectNotTaken() {

        LOGGER.debug("SERVICE: select not taken tickets");
        return ticketDao.selectNotTaken();
    }

    @Override
    public List<Ticket> selectAllTickets() {

        LOGGER.debug("SERVICE: select all tickets");
        return ticketDao.selectAllTickets();
    }

    @Override
    public List<Ticket> selectNotTakenBetweenDates(String dateLowStr, String dateHighStr) throws InvalidDateException {

        LOGGER.debug("SERVICE: select not taken tickets between dates");

        Date dateLow = null;
        Date dateHigh = null;

        if(isValidDate(dateLowStr) && isValidDate(dateHighStr)) {
            dateLow = Date.valueOf(dateLowStr);
            dateHigh = Date.valueOf(dateHighStr);

            if (dateLow.compareTo(dateHigh) > 0) {
                //swap these dates
                Date tempDate = dateLow;
                dateLow = dateHigh;
                dateHigh = tempDate;
            }
        }
        else {
            throw new InvalidDateException("error: parsing dates",dateHighStr + dateLowStr);
        }
        return ticketDao.selectNotTakenBetweenDates(dateLow, dateHigh);
    }

    @Override
    public List<Ticket> selectNotTakenByDateAndTitle(Date date, String title) {

        LOGGER.debug("SERVICE: select not taken by date and title");
        return ticketDao.selectNotTakenByDateAndTitle(date, title);
    }

    @Override
    public List<Ticket> selectNotTakenByTitle(String title) {

        LOGGER.debug("SERVICE: select not taken by title");
        return ticketDao.selectNotTakenByTitle(title);
    }

    @Override
    public List<Customer> getCustomersByDate(Date date) {

        LOGGER.debug("SERVICE: select customers that bought tickets on specific date");
        return ticketDao.getCustomersByDate(date);
    }

    @Override
    public Customer getCustomersByDateAndNumber(Date date, String number) {

        LOGGER.debug("SERVICE: get Customer by Date({}) and number({})", date, number);
        return ticketDao.getCustomersByDateAndNumber(date, number);
    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {

        LOGGER.debug("SERVICE: select tickets of customer with id={}", customerId);
        return ticketDao.getTicketsOfCustomer(customerId);
    }

    @Override
    public TotalCustomerCost getTicketsSumOfCustomer(Long customerId) {

        LOGGER.debug("SERVICE: get total cost of customer with id={}", customerId);
        return ticketDao.getTicketsSumOfCustomer(customerId);
    }

    @Override
    public void updateTicket(Ticket ticket) {

        LOGGER.debug("SERVICE: update ticket={}", ticket);
        ticketDao.updateTicket(ticket);
    }

    @Override
    public void updateSetTakenTrue(Long ticketId, Long customerId) {

        LOGGER.debug("SERVICE: update ticket, set taken true, ticket_id={}", ticketId);
        ticketDao.updateSetTakenTrue(ticketId, customerId);
    }

    @Override
    public void updateTicketsWhenCustomerRemoved(Long customerId) {

        LOGGER.debug("SERVICE: update tickets when customer removed");
        ticketDao.updateTicketsWhenCustomerRemoved(customerId);
    }

    @Override
    public Ticket selectTicketById(Long ticketId) {

        LOGGER.debug("SERVICE: select ticket by id={}", ticketId);
        return ticketDao.selectTicketById(ticketId);
    }

    @Override
    public Boolean checkTicketExistence(String date, String title, Long location) throws InvalidDateException{

        LOGGER.debug("SERVICE: check ticket existence");
        if(isValidDate(date)) {
            try {
                ticketDao.checkTicketExistence(Date.valueOf(date), title, location);
                return Boolean.TRUE;
            } catch (EmptyResultDataAccessException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        }
        else {
            throw new InvalidDateException("error: parsing date", date);
        }
    }
}
