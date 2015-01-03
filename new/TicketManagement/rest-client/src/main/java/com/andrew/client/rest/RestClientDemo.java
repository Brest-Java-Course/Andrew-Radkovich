package com.andrew.client.rest;

import com.andrew.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by andrew on 28.12.14.
 */
public class RestClientDemo {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws IOException{

        String host = "http://localhost:8080/web-1.0-SNAPSHOT/";

        CustomerRestClient customerRestClient = new CustomerRestClient(host);
/*
        Customer customer = new Customer();
        customer.setName("NewName" + 1);
        customer.setIdentificationNumber("PIN" + 1);
        Long id = customerRestClient.addCustomer(customer);
        LOGGER.debug("Customer id is = {}", id);
*/
        Customer[] customers = customerRestClient.getAllCustomers();
        LOGGER.debug("{}",customers);
    }
}
