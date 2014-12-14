package com.andrew.dao;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.customer.Customer;
import com.andrew.dao.mapper.CustomerMapper;
import com.andrew.dao.mapper.TicketMapper;
import com.andrew.dao.mapper.TotalCustomerCostMapper;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by andrew on 18.11.14.
 */
@Component
public class TicketDaoImpl implements TicketDao {

    public static final String TICKET_ID = "ticket_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String COST = "cost";
    public static final String LOCATION = "location";
    public static final String DATE = "data";
    public static final String TITLE = "title";
    public static final String TAKEN = "taken";
    public static final String TOTAL_COST = "total_cost";

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
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_not_taken_by_title_path}')).inputStream)}")
    public String selectNotTakenByTitleSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_ticket_by_id_path}')).inputStream)}")
    public String selectTicketByIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_tickets_of_customer_path}')).inputStream)}")
    public String selectTicketsByCustomerIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_sum_path}')).inputStream)}")
    public String selectSumByCustomerIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_ticket_path}')).inputStream)}")
    public String updateTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_set_taken_true_path}')).inputStream)}")
    public String updateSetTakenTrueTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_set_taken_false_path}')).inputStream)}")
    public String updateSetTakenFalseTicketSql;

    private static final Logger LOGGER = LogManager.getLogger();

    //@Autowired
    //private DataSource dataSource;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //@PostConstruct
    public void setDataSource(DataSource dataSource) {

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addTicket(Ticket ticket) {

        LOGGER.debug("add ticket({})", ticket);
        Assert.notNull(ticket);
        Assert.isNull(ticket.getTicketId());
        Assert.notNull(ticket.getCost(), "cost should be specified");
        Assert.notNull(ticket.getDate(), "date should be specified");
        Assert.notNull(ticket.getLocation(), "location should be specified");
        Assert.notNull(ticket.getTitle(), "title should be specified");
        //Assert.notNull(ticket.getCustomerId(), "customer id should be specified");
        Assert.notNull(ticket.isTaken(), "ticket can be taken or not taken");

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
    public Customer getCustomersByDateAndNumber(Date date, String number) {

        LOGGER.debug("get customers by date={} and number={}", date, number);
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put(DATE, date);
        parameters.put(CustomerDaoImpl.CUSTOMER_NUMBER, number);
        return namedParameterJdbcTemplate.queryForObject(getSelectCustomersByDateAndNumberSql, parameters, new CustomerMapper());
    }

    @Override
    public TotalCustomerCost getTicketsSumOfCustomer(Long customerId) {

        LOGGER.debug("get tickets sum of customer = {}", customerId);
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put(CUSTOMER_ID, customerId);
        return namedParameterJdbcTemplate.query(selectSumByCustomerIdSql, parameters, new TotalCustomerCostMapper()).get(0);
    }

    @Override
    public void updateTicket(Ticket ticket) {

        LOGGER.debug("update whole ticket({})", ticket);
        Map<String, Object> parameters = new HashMap<String, Object>(7);
        parameters.put(TICKET_ID, ticket.getTicketId());
        parameters.put(CUSTOMER_ID, ticket.getCustomerId());
        parameters.put(TITLE, ticket.getTitle());
        parameters.put(COST, ticket.getCost());
        parameters.put(LOCATION, ticket.getLocation());
        parameters.put(DATE, ticket.getDate());
        parameters.put(TAKEN, ticket.isTaken());
        namedParameterJdbcTemplate.update(updateTicketSql, parameters);
    }

    @Override
    public void updateSetTakenTrue(Long ticketId) {

        LOGGER.debug("set taken true {}", ticketId);
        Map<String, Long> parameter = new HashMap<String, Long>(1);
        parameter.put(TICKET_ID, ticketId);
        namedParameterJdbcTemplate.update(updateSetTakenTrueTicketSql, parameter);
    }

    @Override
    public Ticket selectTicketById(Long ticketId) {

        LOGGER.debug("select ticket by id = {}", ticketId);
        Map<String, Long> parameter = new HashMap<String, Long>(1);
        parameter.put(TICKET_ID, ticketId);
        return namedParameterJdbcTemplate.queryForObject(selectTicketByIdSql, parameter, new TicketMapper());
    }

    @Override
    public void updateTicketSetTakenFalse(Long ticketId) {

        LOGGER.debug("update ticket: set taken false");
        Map<String, Long> parameter = new HashMap<String, Long>(1);
        parameter.put(TICKET_ID, ticketId);
        namedParameterJdbcTemplate.update(updateSetTakenFalseTicketSql, parameter);
    }

    @Override
    public void removeTicket(Long ticketId) {

        LOGGER.debug("remove ticket {}", ticketId);
        Map<String, Long> parameter = new HashMap<String, Long>(1);
        parameter.put(TICKET_ID, ticketId);
        namedParameterJdbcTemplate.update(removeTicketSql, parameter);
    }

    @Override
    public List<Ticket> selectNotTaken() {

        LOGGER.debug("select not taken");
        return namedParameterJdbcTemplate.query(selectAllNotTakenTicketsSql, new TicketMapper());
    }

    @Override
    public List<Ticket> selectAllTickets() {

        LOGGER.debug("select all tickets");
        return namedParameterJdbcTemplate.query(selectAllTicketsSql, new TicketMapper());
    }

    @Override
    public List<Ticket> selectNotTakenByDate(Date date) {

        LOGGER.debug("select not taken by date={}", date);
        Map<String, Date> parameter = new HashMap<String, Date>(1);
        parameter.put(DATE, date);
        return namedParameterJdbcTemplate.query(selectNotTakenByDateSql, parameter, new TicketMapper());
    }

    @Override
    public List<Ticket> selectNotTakenByDateAndTitle(Date date, String title) {

        LOGGER.debug("select not taken by date={} and title={}", date, title);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(DATE, date, Types.DATE);
        parameters.addValue(TITLE, title);
        /*Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put(DATE, date, Types.VARCHAR);
        parameters.put(TITLE, title);*/
        return namedParameterJdbcTemplate.query(selectNotTakenByDateAndTitleSql, parameters, new TicketMapper());
    }

    @Override
    public List<Ticket> selectNotTakenByTitle(String title) {

        LOGGER.debug("select not taken by title={}", title);
        Map<String, String> parameter = new HashMap<String, String>(1);
        parameter.put(TITLE, title);
        return namedParameterJdbcTemplate.query(selectNotTakenByTitleSql, parameter, new TicketMapper());
    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {

        LOGGER.debug("select tickets of customer={}", customerId);
        Map<String, Long> parameter = new HashMap<String, Long>(1);
        parameter.put(CUSTOMER_ID, customerId);
        return namedParameterJdbcTemplate.query(selectTicketsByCustomerIdSql, parameter, new TicketMapper());
    }
}