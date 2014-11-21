package com.andrew.dao.mapper;

import com.andrew.dao.TicketDaoImpl;
import com.andrew.ticket.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 21.11.14.
 */

public class TicketMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setCustomerId(resultSet.getLong(TicketDaoImpl.CUSTOMER_ID));
        ticket.setCost(resultSet.getInt(TicketDaoImpl.COST));
        ticket.setDate(resultSet.getDate(TicketDaoImpl.DATE));
        ticket.setLocation(resultSet.getInt(TicketDaoImpl.LOCATION));
        ticket.setTitle(resultSet.getString(TicketDaoImpl.TITLE));
        ticket.setTicketId(resultSet.getLong(TicketDaoImpl.TICKET_ID));
        ticket.setTaken(resultSet.getBoolean(TicketDaoImpl.TAKEN));
        return ticket;
    }
}