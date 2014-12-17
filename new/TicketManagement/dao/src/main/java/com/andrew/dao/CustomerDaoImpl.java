package com.andrew.dao;

import com.andrew.customer.Customer;
import com.andrew.dao.mapper.CustomerMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 18.11.14.
 */
@Component
public class CustomerDaoImpl implements CustomerDao{

    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NUMBER = "identificationNumber";

    //public static final String addNewCustomerSql = "INSERT INTO CUSTOMER (customer_id, name, identificationNumber) VALUES (:customer_id, :name, :identificationNumber);";

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_into_customer_path}')).inputStream)}")
    public String addNewCustomerSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_customers_path}')).inputStream)}")
    public String selectAllCustomersSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_customer_path}')).inputStream)}")
    public String updateCustomerSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_customer}')).inputStream)}")
    public String removeCustomerSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_customer_by_id}')).inputStream)}")
    public String selectCustomerByIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_customer_by_identification_number}')).inputStream)}")
    public String selectCustomerByNumberSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_if_customer_was_removed_path}')).inputStream)}")
    public String updateIfCustomerWasRemovedSql;

    private static final Logger LOGGER = LogManager.getLogger();

    //@Autowired
    //private DataSource dataSource;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //@PostConstruct
    public void setDataSource(DataSource dataSource) {

        LOGGER.debug("DAO: setting datasource");
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addCustomer(Customer customer) {

        LOGGER.debug("DAO: add customer({})", customer);
        Assert.notNull(customer);
        Assert.isNull(customer.getCustomerId());
        Assert.notNull(customer.getName(), "Customer name should be specified");
        Assert.notNull(customer.getIdentificationNumber(), "Identification Number should be specified");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(CUSTOMER_ID, customer.getCustomerId());
        sqlParameterSource.addValue(CUSTOMER_NUMBER, customer.getIdentificationNumber());
        sqlParameterSource.addValue(CUSTOMER_NAME, customer.getName());

        namedParameterJdbcTemplate.update(addNewCustomerSql, sqlParameterSource, keyHolder);

        Long id = keyHolder.getKey().longValue();
        LOGGER.debug("DAO: new customer id={}", id);
        return id;
    }

    @Override
    public List<Customer> getCustomers() {

        LOGGER.debug("DAO: get customers");
        return namedParameterJdbcTemplate.query(selectAllCustomersSql, new CustomerMapper());
    }

    @Override
    public void removeCustomer(Long id) {

        LOGGER.debug("DAO: remove customer with id={}", id);
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put(CUSTOMER_ID, id);
        namedParameterJdbcTemplate.update(updateIfCustomerWasRemovedSql, parameters);
        namedParameterJdbcTemplate.update(removeCustomerSql, parameters);
    }

    @Override
    public Customer getCustomerById(Long id) {

        LOGGER.debug("DAO: get customer by Id={}", id);
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put(CUSTOMER_ID, id);
        return namedParameterJdbcTemplate.queryForObject(selectCustomerByIdSql, parameters, new CustomerMapper());
    }

    @Override
    public Customer getCustomerByNumber(String identificationNumber) {

        LOGGER.debug("DAO: get customer by Number={}", identificationNumber);
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(CUSTOMER_NUMBER, identificationNumber);
        return namedParameterJdbcTemplate.queryForObject(selectCustomerByNumberSql, parameters, new CustomerMapper());
    }

    @Override
    public void updateCustomer(Customer customer) {

        LOGGER.debug("DAO: update customer({})", customer);
        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put(CUSTOMER_ID, customer.getCustomerId());
        parameters.put(CUSTOMER_NAME, customer.getName());
        parameters.put(CUSTOMER_NUMBER, customer.getIdentificationNumber());
        namedParameterJdbcTemplate.update(updateCustomerSql, parameters);
    }
}
