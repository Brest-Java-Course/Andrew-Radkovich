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
    public void getCustomers() {

        List<Customer> customers = customerDao.getCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    public void addCustomer() {

        List<Customer> customers = customerDao.getCustomers();
        int sizeBefore = customers.size();

        Customer customer = new Customer();
        customer.setIdentificationNumber("ABC");
        customer.setName("Robin Good");

        customerDao.addCustomer(customer);

        customers = customerDao.getCustomers();
        assertEquals(sizeBefore, customers.size() - 1);
    }

    @Test
    public void removeCustomer() {

        List<Customer> customers = customerDao.getCustomers();
        int sizeBefore = customers.size();

        customerDao.removeCustomer(1L);

        customers = customerDao.getCustomers();
        assertEquals(sizeBefore, customers.size() + 1);
    }

    @Test
    public void updateCustomer() {

        Customer customer = new Customer();
        customer.setIdentificationNumber("AB3");
        customer.setName("Bruce Wayne");

        Long id = customerDao.addCustomer(customer);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(id);
        updatedCustomer.setIdentificationNumber("AB3");
        updatedCustomer.setName("Batman");

        customerDao.updateCustomer(updatedCustomer);

        customer = customerDao.getCustomerById(id);

        assertEquals(customer, updatedCustomer);
    }

    @Test
    public void getCustomerById() {

        Customer customer = new Customer();
        customer.setIdentificationNumber("ABCD");
        customer.setName("Luke Skywalker");

        Long id = customerDao.addCustomer(customer);
        customer.setCustomerId(id);

        Customer gottenCustomer = customerDao.getCustomerById(id);

        assertEquals(customer, gottenCustomer);
    }

    @Test
    public void getCustomerByNumber() {

        Customer customer = new Customer();
        customer.setIdentificationNumber("ABCDEFG");
        customer.setName("William Turner");

        Long id = customerDao.addCustomer(customer);
        customer.setCustomerId(id);

        Customer gottenCustomer = customerDao.getCustomerByNumber("ABCDEFG");

        assertEquals(customer, gottenCustomer);
    }
}
