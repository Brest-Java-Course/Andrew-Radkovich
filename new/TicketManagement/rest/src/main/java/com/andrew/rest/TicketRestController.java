package com.andrew.rest;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.service.TicketService;
import com.andrew.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @RequestMapping(value = "/update/whenCustomerRemoved/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateTicketsWhenCustomerRemoved(@PathVariable Long id) {

        ticketService.updateTicketsWhenCustomerRemoved(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> addTicket(@RequestBody Ticket ticket) {

        Long id = ticketService.addTicket(ticket);
        return new ResponseEntity<Long>(id, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/setTakenTrue/{ticketId}/{customerId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity sellTicketToCustomer(@PathVariable Long ticketId,
                                               @PathVariable Long customerId) {

        ticketService.updateSetTakenTrue(ticketId, customerId);
        return new ResponseEntity("sold to customer({})" + customerId, HttpStatus.OK);
    }

    @RequestMapping(value = "/check/{date}/{title}/{location}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity checkTicketExistence(@PathVariable String date,
                                                        @PathVariable String title,
                                                        @PathVariable Long location) {

        Boolean exists = null;
        try {
            exists = ticketService.checkTicketExistence(date, title, location);
        } catch (InvalidDateException e) {
            e.printStackTrace();
            return new ResponseEntity("invalid date", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(exists, HttpStatus.OK);
    }
}
