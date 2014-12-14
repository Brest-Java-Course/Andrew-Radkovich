package com.andrew.web;

import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDao;
import com.andrew.dao.CustomerDaoImpl;
import com.andrew.service.CustomerService;
import com.andrew.service.TicketService;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

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

        LOGGER.debug("showing home page");
        model.put("Customers", customerService.getCustomers());
        return "home";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editCustomer(@RequestParam("id")Long id) {

        Customer customer = customerService.getCustomerById(id);
        List<Ticket> ticketsOfCustomer = ticketService.getTicketsOfCustomer(id);
        ModelAndView modelAndView = new ModelAndView("edit", "customer", customer);
        modelAndView.addObject("ticketsOfCustomer", ticketsOfCustomer);
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

        customerService.updateCustomer(customer);
        return new ModelAndView("edit", "customer", customer);
    }

    @RequestMapping(value = "removeCustomer", method = RequestMethod.POST)
    public String removeCustomer(@RequestParam("customerId")Long id) {

        customerService.removeCustomer(id);
        return "redirect:/home";
    }
}