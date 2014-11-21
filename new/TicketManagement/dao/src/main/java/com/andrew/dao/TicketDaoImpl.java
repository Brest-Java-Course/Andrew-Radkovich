package com.andrew.dao;

import com.andrew.customer.Customer;
import com.andrew.dao.mapper.CustomerMapper;
import com.andrew.dao.mapper.SumMapper;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by andrew on 18.11.14.
 */
public class TicketDaoImpl implements TicketDao {

    public static final String TICKET_ID = "ticket_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String COST = "cost";
    public static final String LOCATION = "location";
    public static final String DATE = "data";
    public static final String TITLE = "title";
    public static final String TAKEN = "taken";

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_into_ticket_path}')).inputStream)}")
    public String addTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_ticket_path}')).inputStream)}")
    public String removeTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_not_taken_path}')).inputStream)}")
    public String selectAllNotTakenTicketsSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_path}')).inputStream)}")
    public String selectAllTicketsSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_customers_by_date_of_performance_path}')).inputStream)}")
    public String selectCustomersByDateSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_customers_by_date_of_performance_and_number_path}')).inputStream)}")
    public String getSelectCustomersByDateAndNumberSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_not_taken_tickets_by_date_path}')).inputStream)}")
    public String selectNotTakenByDateSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_not_taken_tickets_by_date_and_title_path}')).inputStream)}")
    public String selectNotTakenByDateAndTitleSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_not_taken_tickets_by_title_path}')).inputStream)}")
    public String selectNotTakenByTitleSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_tickets_of_customer_path}')).inputStream)}")
    public String selectTicketsByCustomerIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_sum_path}')).inputStream)}")
    public String selectSumByCustomerIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_ticket_path}')).inputStream)}")
    public String updateTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_set_taken_true_path}')).inputStream)}")
    public String updateSetTakenTrueTicketSql;

    private static final Logger LOGGER = LogManager.getLogger();

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addTicket(Ticket ticket) {

        LOGGER.debug("add ticket({})", ticket);
        Assert.notNull(ticket);
        Assert.isNull(ticket.getTicketId(), "ticket id should be specified");
        Assert.notNull(ticket.getCost(), "cost should be specified");
        Assert.notNull(ticket.getDate(), "date should be specified");
        Assert.notNull(ticket.getLocation(), "location should be specified");
        Assert.notNull(ticket.getTitle(), "title should be specified");
        Assert.notNull(ticket.getCustomerId(), "customer id should be specified");
        Assert.notNull(ticket.isTaken(), "ticket should be taken or not taken");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(TICKET_ID, ticket.getTicketId());
        sqlParameterSource.addValue(CUSTOMER_ID, ticket.getCustomerId());
        sqlParameterSource.addValue(COST, ticket.getCost());
        sqlParameterSource.addValue(TITLE, ticket.getTitle());
        sqlParameterSource.addValue(LOCATION, ticket.getLocation());
        sqlParameterSource.addValue(TAKEN, ticket.isTaken());
        sqlParameterSource.addValue(DATE, ticket.getDate());

        namedParameterJdbcTemplate.update(addTicketSql, sqlParameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Customer> getCustomersByDate(Date date) {

        LOGGER.debug("select customers by date of performance");
        Map<String, Date> parameterSource = new HashMap<String, Date>(1);
        parameterSource.put(DATE, date);
        return namedParameterJdbcTemplate.query(selectCustomersByDateSql, parameterSource, new CustomerMapper());
    }

    @Override
    public List<Customer> getCustomersByDateAndNumber(Date date, String number) {
        return null;
    }

    @Override
    public Long getTicketsSumOfCustomer(Long customerId) {

        LOGGER.debug("get tickets sum of customer = {}", customerId);
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put(CUSTOMER_ID, customerId);
        return namedParameterJdbcTemplate.
    }

    @Override
    public void updateTicket(Ticket ticket) {

    }

    @Override
    public void updateSetTakenTrue(Long ticketId) {

    }

    @Override
    public void removeTicket(Long ticketId) {

    }

    @Override
    public List<Ticket> selectNotTaken() {
        return null;
    }

    @Override
    public List<Ticket> selectAllTickets() {
        return null;
    }

    @Override
    public List<Ticket> selectNotTakenByDate(Date date) {
        return null;
    }

    @Override
    public List<Ticket> selectNotTakenByDateAndTitle(Date date, String title) {
        return null;
    }

    @Override
    public List<Ticket> selectNotTakenByTitle(String title) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {
        return null;
    }
}
