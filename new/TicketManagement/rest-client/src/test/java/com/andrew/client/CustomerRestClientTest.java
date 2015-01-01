package com.andrew.client;

import com.andrew.customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.servlet.view.JstlView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.andrew.client.CustomerRestClientTest.CustomerDataFixture.getCustomers;
import static com.andrew.client.CustomerRestClientTest.CustomerDataFixture.getExistingCustomer;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by andrew on 31.12.14.
 */
public class CustomerRestClientTest {

    private final String HOST = "http://localhost:8080/web-1.0-SNAPSHOT/";

    private static final String CUSTOMER_REST_ROOT = "rest/customer";

    private CustomerRestClient customerRestClient;

    private MockRestServiceServer server;

    @Before
    public void setup() {

        customerRestClient = new CustomerRestClient(HOST);
        server = MockRestServiceServer.createServer(customerRestClient.getRestTemplate());
    }

    @After
    public void check() {

        server.verify();
    }

    @Test
    public void addCustomerRestTest() throws JsonProcessingException {

        Customer newCustomer = getExistingCustomer(5L);
        newCustomer.setCustomerId(null);

        ObjectMapper mapper = new ObjectMapper();
        String customerJson = mapper.writeValueAsString(newCustomer);

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/create"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(customerJson))
                .andRespond(withSuccess("5", MediaType.APPLICATION_JSON));

        long id = customerRestClient.addCustomer(newCustomer);
        assertEquals(5, id);
    }

    @Test
    public void removeCustomerRestTest() {

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/remove/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess("deleted: 1", MediaType.APPLICATION_JSON));

        customerRestClient.removeCustomer(1L);
    }

    @Test
    public void updateCustomerRestTest() throws JsonProcessingException {

        Customer customer = getExistingCustomer(1L);

        ObjectMapper mapper = new ObjectMapper();
        String customerJson = mapper.writeValueAsString(customer);

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/update"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(customerJson))
                .andRespond(withSuccess("updated: " + customer.getCustomerId(), MediaType.APPLICATION_JSON));

        customerRestClient.updateCustomer(customer);
    }

    @Test
    public void getCustomerByIdRestTest() throws JsonProcessingException {

        Customer expectedCustomer = getExistingCustomer(1L);

        ObjectMapper mapper = new ObjectMapper();
        String customerJson = mapper.writeValueAsString(expectedCustomer);

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customerJson, MediaType.APPLICATION_JSON));

        customerRestClient.getCustomerById(1L);
    }

    @Test
    public void getCustomerByIdentificationNumberRestTest() throws JsonProcessingException {

        Customer expectedCustomer = getExistingCustomer(1L);

        ObjectMapper mapper = new ObjectMapper();
        String customerJson = mapper.writeValueAsString(expectedCustomer);

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/number/AB1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customerJson, MediaType.APPLICATION_JSON));

        customerRestClient.getCustomerByNumber("AB1");
    }

    @Test
    public void getAllCustomersRestTest() throws JsonProcessingException {

        List<Customer> customers = getCustomers();

        ObjectMapper mapper = new ObjectMapper();
        String customersJson = mapper.writeValueAsString(customers);

        server.expect(requestTo(HOST + CUSTOMER_REST_ROOT + "/all"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customersJson, MediaType.APPLICATION_JSON));

        customerRestClient.getAllCustomers();
    }

    public static class CustomerDataFixture {

        public static Customer getExistingCustomer(Long id) {

            Customer customer = new Customer();
            customer.setCustomerId(id);
            customer.setName("name" + id);
            customer.setIdentificationNumber("number" + id);
            return customer;
        }

        public static List<Customer> getCustomers() {

            List<Customer> customers = new ArrayList<Customer>();
            customers.add(getExistingCustomer(1L));
            customers.add(getExistingCustomer(2L));
            customers.add(getExistingCustomer(3L));

            return customers;
        }
    }
}
