package com.andrew.dao.mapper;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.dao.CustomerDaoImpl;
import com.andrew.dao.TicketDaoImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 22.11.14.
 */
public class TotalCustomerCostMapper implements RowMapper<TotalCustomerCost> {
    @Override
    public TotalCustomerCost mapRow(ResultSet resultSet, int i) throws SQLException {

        TotalCustomerCost customerCost = new TotalCustomerCost();
        customerCost.setTotalCost(resultSet.getLong(TicketDaoImpl.TOTAL_COST));

        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong(CustomerDaoImpl.CUSTOMER_ID));
        customer.setName(resultSet.getString(CustomerDaoImpl.CUSTOMER_NAME));
        customer.setIdentificationNumber(resultSet.getString(CustomerDaoImpl.CUSTOMER_NUMBER));

        customerCost.setCustomer(customer);

        return customerCost;
    }
}
