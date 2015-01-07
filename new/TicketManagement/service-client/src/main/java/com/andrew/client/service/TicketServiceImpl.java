package com.andrew.client.service;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.client.rest.CustomerRestClient;
import com.andrew.client.rest.TicketRestClient;
import com.andrew.client.service.exception.InvalidDateException;
import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

import static com.andrew.client.service.DateValidator.isValidDate;
import static java.util.Arrays.asList;

/**
 * Created by andrew on 2.1.15.
 */
public class TicketServiceImpl implements TicketService {

    public static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerRestClient customerRestClient;

    @Autowired
    private TicketRestClient ticketRestClient;

    public TicketRestClient getTicketRestClient() {

        return ticketRestClient;
    }

    public void setTicketRestClient(TicketRestClient ticketRestClient) {

        this.ticketRestClient = ticketRestClient;
    }

    public CustomerRestClient getCustomerRestClient() {

        return customerRestClient;
    }

    public void setCustomerRestClient(CustomerRestClient customerRestClient) {

        this.customerRestClient = customerRestClient;
    }

    @Override
    public void addTicket(Ticket ticket) {

        ticketRestClient.addTicket(ticket);
    }

    @Override
    public List<Ticket> selectNotTaken() {

        return asList(ticketRestClient.getNotTakenTickets());
    }

    @Override
    public List<Ticket> selectNotTakenBetweenDates(String dateLow, String dateHigh) {

        return asList(ticketRestClient.getNotTakenBetweenDates(dateLow, dateHigh));
    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {

        return asList(ticketRestClient.getTicketsOfCustomer(customerId));
    }

    @Override
    public Integer getTicketsSumOfCustomer(Long customerId) {

        return ticketRestClient.getTicketSumOfCustomer(customerId);
    }

    @Override
    public void updateSetTakenTrue(Long ticketId, Long customerId) {

        ticketRestClient.updateTicketSetTakenTrue(ticketId, customerId);
    }

    @Override
    public Boolean checkTicketExistence(String dateStr, String title, Long location) {

        LOGGER.debug("SERVICE: check ticket existence");
        if(isValidDate(dateStr)) {
            return ticketRestClient.checkTicketExistence(Date.valueOf(dateStr), title, location);
        }
        else {
            throw new InvalidDateException("error: parsing date", dateStr);
        }
    }

    @Override
    public Long countTicketsOfCustomer(Long customerId) {

        return ticketRestClient.countTicketsOfCustomer(customerId);
    }
}
