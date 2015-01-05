package com.andrew.rest;

import com.andrew.customer.Customer;
import com.andrew.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.andrew.rest.CustomerRestMockTest.CustomerDataFixture.getCustomers;
import static com.andrew.rest.CustomerRestMockTest.CustomerDataFixture.getExistingCustomer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by andrew on 18.12.14.
 */
public class CustomerRestMockTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerRestController customerRestController;

    @Mock
    private CustomerService customerService;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(customerRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getCustomerRestTest() throws Exception {

        Customer customer = getExistingCustomer(1L);
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/rest/customer/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"customerId\":1,\"name\":\"name1\",\"identificationNumber\":\"number1\"}"
                ));

        verify(customerService).getCustomerById(1L);
    }

    @Test
    public void getCustomerByNumberRestTest() throws Exception {

        Customer expectedCustomer = getExistingCustomer(1L);
        when(customerService.getCustomerByNumber(expectedCustomer.getIdentificationNumber())).thenReturn(expectedCustomer);

        mockMvc.perform(get("/rest/customer/number/" + expectedCustomer.getIdentificationNumber())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"customerId\":1,\"name\":\"name1\",\"identificationNumber\":\"" + expectedCustomer.getIdentificationNumber() + "\"}"
                ));
        verify(customerService).getCustomerByNumber(expectedCustomer.getIdentificationNumber());
    }

    @Test
    public void getCustomersRestTest() throws Exception {

        List<Customer> expectedCustomers = getCustomers();
        when(customerService.getCustomers()).thenReturn(expectedCustomers);

        mockMvc.perform(get("/rest/customer/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"customerId\":1,\"name\":\"name1\",\"identificationNumber\":\"number1\"}," +
                        "{\"customerId\":2,\"name\":\"name2\",\"identificationNumber\":\"number2\"}," +
                        "{\"customerId\":3,\"name\":\"name3\",\"identificationNumber\":\"number3\"}]"
                ));

        verify(customerService).getCustomers();
    }

    @Test
    public void addCustomerRestTest() throws Exception {

        Long customerId = 1L;
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customerId);

        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(CustomerDataFixture.getExistingCustomer(customerId));

        mockMvc.perform(
                post("/rest/customer/create")
                .content(customerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(customerId.toString()));

        verify(customerService).addCustomer(any(Customer.class));
    }

    @Test
    public void removeCustomerRestTest() throws Exception {

        doNothing().when(customerService).removeCustomer(1L);

        mockMvc.perform(delete("/rest/customer/remove/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService).removeCustomer(1L);
    }

    @Test
    public void updateCustomerRestTest() throws Exception {

        doNothing().when(customerService).updateCustomer(any(Customer.class));

        ObjectMapper mapper = new ObjectMapper();
        Customer customer = new Customer();
        customer.setName("NewName");
        String customerJson = mapper.writeValueAsString(customer);

        mockMvc.perform(put("/rest/customer/update")
                .content(customerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService).updateCustomer(any(Customer.class));
    }

    @Test
    public void checkExistingCustomer() throws Exception {

        when(customerService.checkExistingCustomerByNumber(anyString())).thenReturn(Boolean.TRUE);

        mockMvc.perform(get("/rest/customer/check/AB1"))
                .andDo(print())
                .andExpect(content().string(Boolean.TRUE.toString()))
                .andExpect(status().isOk());

        verify(customerService).checkExistingCustomerByNumber(anyString());
    }

    @Test
    public void checkNotExistingCustomer() throws Exception {

        when(customerService.checkExistingCustomerByNumber(anyString())).thenReturn(Boolean.FALSE);

        mockMvc.perform(get("/rest/customer/check/AB1"))
                .andExpect(content().string(Boolean.FALSE.toString()))
                .andExpect(status().isOk());
        verify(customerService).checkExistingCustomerByNumber(anyString());
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
