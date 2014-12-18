package com.andrew.rest;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.service.TicketService;
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

        try {
            Ticket ticket = ticketService.selectTicketById(id);
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

    @RequestMapping(value = "/notTaken/date/{date}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getNotTakenByDate(@PathVariable String date) {

        List<Ticket> tickets = ticketService.selectNotTakenByDate(Date.valueOf(date));
        return new ResponseEntity(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/notTaken/title/{title}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getNotTakenTicketsByTitle(@PathVariable String title) {

        List<Ticket> tickets = ticketService.selectNotTakenByTitle(title);
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
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
