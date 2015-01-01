package com.andrew.rest;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.service.TicketService;
import com.andrew.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrew on 17.12.14.
 */
@Controller
@RequestMapping(value = "/rest/ticket")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {

        Ticket ticket = null;
        try {
            ticket = ticketService.selectTicketById(id);
            return new ResponseEntity(ticket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Ticket with such id(" + id + ") was not found. Error: "
                    + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/notTaken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getNotTaken() {

        List<Ticket> tickets = ticketService.selectNotTaken();
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getAllTickets() {

        List<Ticket> tickets = ticketService.selectAllTickets();
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/notTaken/title/{title}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getNotTakenTicketsByTitle(@PathVariable String title) {

        List<Ticket> tickets = ticketService.selectNotTakenByTitle(title);
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/notTaken/between/{dateLow}/{dateHigh}")
    @ResponseBody
    public ResponseEntity<List<Ticket>> getNotTakenTicketsBetweenDates(@PathVariable String dateLow,
                                                                       @PathVariable String dateHigh) {

        List<Ticket> tickets = null;
        try {
            tickets = ticketService.selectNotTakenBetweenDates(dateLow, dateHigh);
            return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage() + dateLow + " " + dateHigh, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/ofCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getTicketsOfCustomer(@PathVariable Long id) {

        List<Ticket> tickets = ticketService.getTicketsOfCustomer(id);
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/ofCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Integer> getTicketSumOfCustomer(@PathVariable Long id) {

        TotalCustomerCost cost = ticketService.getTicketsSumOfCustomer(id);
        Integer sum = cost.getTotalCost();
        return new ResponseEntity<Integer>(sum, HttpStatus.OK);
    }
}
