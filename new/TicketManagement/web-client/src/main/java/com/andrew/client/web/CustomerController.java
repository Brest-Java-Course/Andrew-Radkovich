package com.andrew.client.web;

import com.andrew.customer.Customer;
import com.andrew.client.service.CustomerService;
import com.andrew.client.service.TicketService;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by andrew on 13.12.14.
 */
@Controller
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
    public String showHomePage(Map<String, Object> model) {

        LOGGER.debug("CONTROLLER: showing home page");
        model.put("Customers", customerService.getCustomers());
        return "home";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editCustomer(@RequestParam("id")Long id) {

        Customer customer = customerService.getCustomerById(id);
        List<Ticket> ticketsOfCustomer = ticketService.getTicketsOfCustomer(id);
        Integer totalCost = ticketService.getTicketsSumOfCustomer(id);
        ModelAndView modelAndView = new ModelAndView("edit", "customer", customer);
        modelAndView.addObject("ticketsOfCustomer", ticketsOfCustomer);
        modelAndView.addObject("totalCost", totalCost);
        return modelAndView;
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public ModelAndView updateCustomer(@RequestParam("id")Long id,
                                       @RequestParam("name")String name,
                                       @RequestParam("pid")String pid) {

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setName(name);
        customer.setIdentificationNumber(pid);

        try {
            customerService.updateCustomer(customer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ModelAndView("error/addExistingCustomer");
        }
        return new ModelAndView("redirect:/home", "customer", customer);
    }

    @RequestMapping(value = "removeCustomer", method = RequestMethod.POST)
    public String removeCustomer(@RequestParam("customerId")Long id) {

        customerService.removeCustomer(id);
        return "redirect:/home";
    }

    @RequestMapping(value = "searchByPid", method = RequestMethod.GET)
    public ModelAndView searchCustomerByNumber(@RequestParam("pid")String pid) {

        LOGGER.debug("CONTROLLER: search customer by number");
        ModelAndView modelAndView = new ModelAndView("newOrder");
        Customer customer = customerService.getCustomerByNumber(pid);
        modelAndView.addObject("customers", asList(customer));
        modelAndView.addObject("tickets", ticketService.selectNotTaken());
        return modelAndView;
    }

    @RequestMapping(value = "/addCustomerForm", method = RequestMethod.GET)
    public ModelAndView getAddCustomerFormView() {

        LOGGER.debug("CONTROLLER: showing add customer form");
        ModelAndView modelAndView = new ModelAndView("addCustomerForm", "customer", new Customer());
        return modelAndView;
    }

    @RequestMapping(value = "addCustomer")
    public ModelAndView addCustomer(@RequestParam("name")String name,
                                    @RequestParam("pid")String pid) {

        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setIdentificationNumber(pid);
        LOGGER.debug("CONTROLLER: add customer={}", customer);
        Long id = null;
        try {
            id = customerService.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ModelAndView("error/addExistingCustomer");
        }
        LOGGER.debug("CONTROLLER: new customer id = {}", id);
        return modelAndView;
    }
}