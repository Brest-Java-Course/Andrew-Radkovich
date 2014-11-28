package com.andrew.dao;

import com.andrew.customer.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andrew on 26.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-dao-test.xml" })
public class CustomerDaoImplTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void getCustomersTest() {

        List<Customer> customers = customerDao.getCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }
/*
    @Test
    public void addCustomerTest() {

        List<Customer> customers = customerDao.getCustomers();
        int sizeBefore = customers.size();

        Customer customer = new Customer();
        customer.setIdentificationNumber("ABC");
        customer.setName("Robin Good");

        customerDao.addCustomer(customer);

        customers = customerDao.getCustomers();
        assertEquals(sizeBefore, customers.size() - 1);
    }
    */
}
