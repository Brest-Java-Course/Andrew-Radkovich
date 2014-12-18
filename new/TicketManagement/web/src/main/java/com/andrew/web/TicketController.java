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

import static com.andrew.web.DateValidator.isValidDate;
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

        Date dateOfPerformance = null;
        Customer customer = null;
        ModelAndView view = new ModelAndView("newOrder");
        if("".equals(date)) {

            view.addObject("tickets", ticketService.selectNotTaken());
        }
        else {
            if( isValidDate(date)) {
                dateOfPerformance = Date.valueOf(date);
                view.addObject("tickets", ticketService.selectNotTakenByDate(dateOfPerformance));
            }
            else {
                ModelAndView invalidDateErrorView = new ModelAndView("error/invalidDate");
                return invalidDateErrorView;
            }
        }
        if("".equals(pid)) {
            view.addObject("customers", customerService.getCustomers());
        }
        else {
            customer = customerService.getCustomerByNumber(pid);
            view.addObject("customers", asList(customer));
        }
        return view;
    }
}