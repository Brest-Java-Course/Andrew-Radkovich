package com.andrew.dao.mapper;

import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDaoImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 21.11.14.
 */
public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong(CustomerDaoImpl.CUSTOMER_ID));
        customer.setName(resultSet.getString(CustomerDaoImpl.CUSTOMER_NAME));
        customer.setIdentificationNumber(resultSet.getString(CustomerDaoImpl.CUSTOMER_NUMBER));
        return customer;
    }
}