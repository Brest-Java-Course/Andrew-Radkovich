package com.andrew.rest;

import com.andrew.customer.Customer;
import com.andrew.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by andrew on 17.12.14.
 */
@Controller
@RequestMapping(value = "/rest/customer")
public class CustomerRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

        try {
            Customer customer = customerService.getCustomerById(id);
            return new ResponseEntity(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Customer not found " + id + "error:" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/number/{number}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Customer> getCustomerByNumber(@PathVariable String number) {

        Customer customer = customerService.getCustomerByNumber(number);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Customer>> getCustomers() {

        List<Customer> customers = customerService.getCustomers();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> addCustomer(@RequestBody Customer customer) {

        Long id = customerService.addCustomer(customer);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {

        customerService.updateCustomer(customer);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeCustomer(@PathVariable Long id) {

        customerService.removeCustomer(id);
        return new ResponseEntity("deleted" + id, HttpStatus.OK);
    }
}
