package com.andrew.web;

import com.andrew.customer.Customer;
import com.andrew.service.CustomerService;
import com.andrew.service.TicketService;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

/**
 * Created by andrew on 14.12.14.
 */
@Controller
public class TicketController {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/newOrder", method = RequestMethod.GET)
    public ModelAndView newOrder() {

        ModelAndView modelAndView = new ModelAndView("newOrder");
        modelAndView.addObject("customers", customerService.getCustomers());
        modelAndView.addObject("tickets", ticketService.selectNotTaken());
        return modelAndView;
    }

    @RequestMapping(value = "/placeOrder", method = RequestMethod.GET)
    public ModelAndView placeOrder(@RequestParam("ticketIdList")Long[] ticketIdList,
                                   @RequestParam("customerId")Long customerId) {

        ModelAndView modelAndView = new ModelAndView("placeOrder");
        for(Long ticketId : ticketIdList) {
            ticketService.updateSetTakenTrue(ticketId, customerId);
        }
        Integer totalCost = ticketService.getTicketsSumOfCustomer(customerId).getTotalCost();
        modelAndView.addObject("totalCost", totalCost);
        modelAndView.addObject("customer", customerService.getCustomerById(customerId));
        modelAndView.addObject("ticketsOfCustomer", ticketService.getTicketsOfCustomer(customerId));
        return modelAndView;
    }

    @RequestMapping(value = "/filterByDateAndPid", method = RequestMethod.GET)
    public ModelAndView filterByDate(@RequestParam("date")String date,
                                     @RequestParam("pid")String pid) {

        //TODO: date validation
        Date dateOfPerformance = Date.valueOf(date);
        ModelAndView view = new ModelAndView("newOrder");
        Customer customer = customerService.getCustomerByNumber(pid);
        view.addObject("customers", asList(customer));
        List<Ticket> tickets = ticketService.selectNotTakenByDate(dateOfPerformance);
        LOGGER.debug("not taken of specific data={}, size={}, tickets={}", date, tickets.size(), tickets);
        //view.addObject("customers", customerService.getCustomers());
        view.addObject("tickets", tickets);
        return view;
    }
}
