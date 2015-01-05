package com.andrew.client.web;

import com.andrew.customer.Customer;
import com.andrew.client.service.CustomerService;
import com.andrew.client.service.TicketService;
import com.andrew.client.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

import static com.andrew.client.web.DateValidator.isValidDate;
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

    @RequestMapping(value = "/createTicketsForm", method = RequestMethod.GET)
    public ModelAndView getCreateTicketsForm() {

        ModelAndView view = new ModelAndView("createTicketsForm");
        return view;
    }

    @RequestMapping(value = "/createTickets", method = RequestMethod.POST)
    public ModelAndView createTickets(@RequestParam("title")String title,
                                      @RequestParam("locations")Integer locations,
                                      @RequestParam("date")String dateStr,
                                      @RequestParam("cost")Integer cost) {

        if(!isValidDate(dateStr)) {

            return new ModelAndView("error/invalidDate");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/home");

        Ticket newTicket = new Ticket();
        newTicket.setCost(cost);
        newTicket.setCustomerId(null);
        newTicket.setTaken(Boolean.FALSE);
        newTicket.setDate(Date.valueOf(dateStr));
        newTicket.setTitle(title);
        for(int i = 0; i < locations; i++) {
            newTicket.setLocation(i + 1);
            ticketService.addTicket(newTicket);
        }

        return modelAndView;
    }

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
        Integer totalCost = ticketService.getTicketsSumOfCustomer(customerId);
        modelAndView.addObject("totalCost", totalCost);
        modelAndView.addObject("customer", customerService.getCustomerById(customerId));
        modelAndView.addObject("ticketsOfCustomer", ticketService.getTicketsOfCustomer(customerId));
        return modelAndView;
    }

    @RequestMapping(value = "/filterByDateAndPid", method = RequestMethod.GET)
    public ModelAndView filterByDate(@RequestParam("dateLow")String dateLowStr,
                                     @RequestParam("dateHigh")String dateHighStr,
                                     @RequestParam("pid")String pid) throws InvalidDateException{

        Date dateLow = null;
        Date dateHigh = null;
        Customer customer = null;
        ModelAndView view = new ModelAndView("newOrder");

        if("".equals(dateLowStr) || "".equals(dateHighStr)) {
            view.addObject("tickets", ticketService.selectNotTaken());
        }
        else {

            try {
                view.addObject("tickets", ticketService.selectNotTakenBetweenDates(dateLowStr, dateHighStr));
            } catch (Exception e) {
                e.printStackTrace();
                return new ModelAndView("invalidDate");
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