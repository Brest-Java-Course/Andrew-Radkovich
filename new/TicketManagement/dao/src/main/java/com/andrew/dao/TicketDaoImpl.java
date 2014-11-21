package com.andrew.dao;

import com.andrew.customer.Customer;
import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_customers_by_date_of_performance_path}')).inputStream)}")
    public String selectCustomersByDateSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_ticket_path}')).inputStream)}")
    public String updateTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_ticket_path}')).inputStream)}")
    public String removeTicketSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_tickets_of_customer_path}')).inputStream)}")
    public String selectTicketsByCustomerIdSql;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_sum_path}')).inputStream)}")
    public String selectSumByCustomerId;


    private static final Logger LOGGER = LogManager.getLogger();

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addTicket(Ticket ticket) {
        return null;
    }

    @Override
    public List<Customer> getCustomersByDateOfPerformance(String date) {
        return null;
    }

    @Override
    public Long getTicketsSumOfCustomer(Long customerId) {
        return null;
    }

    @Override
    public void updateTicket(Ticket ticket) {

    }

    @Override
    public void removeTicket(Long ticketId) {

    }

    @Override
    public List<Ticket> getTicketsOfCustomer(Long customerId) {
        return null;
    }

    public class TicketMapper implements RowMapper<Ticket> {

        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setCustomer(resultSet.getLong(CUSTOMER_ID));
            ticket.setCost(resultSet.getInt(COST));
            ticket.setDate(resultSet.getDate(DATE));
            ticket.setLocation(resultSet.getInt(LOCATION));
            ticket.setTitle(resultSet.getString(TITLE));
            ticket.setTicketId(resultSet.getLong(TICKET_ID));
            ticket.setTaken(resultSet.getBoolean(TAKEN));
            return ticket;
        }
    }
}
