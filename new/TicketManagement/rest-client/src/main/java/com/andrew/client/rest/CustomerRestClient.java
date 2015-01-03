package com.andrew.client.rest;

import com.andrew.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 28.12.14.
 */
public class CustomerRestClient {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CUSTOMER_REST_ROOT = "/rest/customer";

    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public CustomerRestClient(String host) {

       this.host = host;
       List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
       converters.add(new MappingJackson2HttpMessageConverter());
       restTemplate.setMessageConverters(converters);
   }

    public Long addCustomer(Customer customer) {

        LOGGER.debug("REST CLIENT: add customer = {}", customer);
        return restTemplate.postForObject(host + CUSTOMER_REST_ROOT + "/create", customer, Long.class);
    }

    public Customer getCustomerById(Long id) {

        LOGGER.debug("REST CLIENT: get customer by id = {}", id);
        return restTemplate.getForObject(host + CUSTOMER_REST_ROOT + "/" + id, Customer.class);
    }

    public Customer getCustomerByNumber(String number) {

        LOGGER.debug("REST CLIENT: get customer by number = {}", number);
        return restTemplate.getForObject(host + CUSTOMER_REST_ROOT + "/number/" + number, Customer.class);
    }

    public Customer[] getAllCustomers() {

        LOGGER.debug("REST CLIENT: get all customers");
        return restTemplate.getForObject(host + CUSTOMER_REST_ROOT + "/all", Customer[].class);
    }

    public void updateCustomer(Customer customer) {

        LOGGER.debug("REST CLIENT: update customer = {}", customer);
        restTemplate.put(host + CUSTOMER_REST_ROOT + "/update", customer);
    }

    public void removeCustomer(Long id) {

        LOGGER.debug("REST CLIENT: remove customer with id = {}", id);
        restTemplate.delete(host + CUSTOMER_REST_ROOT + "/remove/" + id);
    }
}
