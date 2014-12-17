package com.andrew.web;

import com.andrew.customer.Customer;
import com.andrew.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by andrew on 10.12.14.
 */

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService customerService;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showAllCustomers() {

        List<Customer> expectedCustomers = asList(new Customer(), new Customer(), new Customer());

        when(customerService.getCustomers()).thenReturn(expectedCustomers);

        Map<String, Object> model = new HashMap<String, Object>();

        String viewName = controller.showHomePage(model);

        assertEquals("home", viewName);
        assertSame(expectedCustomers, model.get("Customers"));
        verify(customerService).getCustomers();
    }

    //@Test
    public void addCustomer() {

        Customer customer = new Customer();
        customer.setIdentificationNumber("AB123");
        customer.setName("Mark1");

        when(customerService.addCustomer(customer)).thenReturn(1L);
        ModelAndView view = controller.addCustomer(customer.getName(), customer.getIdentificationNumber());
        assertEquals(view.getViewName(), "home");
        verify(customerService).addCustomer(customer);
    }
}
