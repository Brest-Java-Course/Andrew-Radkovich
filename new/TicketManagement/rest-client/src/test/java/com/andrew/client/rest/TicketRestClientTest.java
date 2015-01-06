package com.andrew.client.rest;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.ticket.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.andrew.client.rest.TicketRestClientTest.TicketDataFixture.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by andrew on 1.1.15.
 */
public class TicketRestClientTest {

    private final String HOST = "http://localhost:8080/web-1.0-SNAPSHOT/";

    private final String TICKET_ROOT_PATH = "rest/ticket/";

    private TicketRestClient ticketRestClient;

    private MockRestServiceServer server;

    @Before
    public void setup() {

        ticketRestClient = new TicketRestClient(HOST);
        server = MockRestServiceServer.createServer(ticketRestClient.getRestTemplate());
    }

    @After
    public void check() {

        server.verify();
    }

    @Test
    public void getNotTakenTicketsRestTest() throws JsonProcessingException {

        List<Ticket> tickets = getNotTakenTickets();

        ObjectMapper mapper = new ObjectMapper();
        String ticketsJson = mapper.writeValueAsString(tickets);

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "notTaken"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ticketsJson, MediaType.APPLICATION_JSON));

        ticketRestClient.getNotTakenTickets();
    }

    @Test
    public void getNotTakenBetweenDatesRestTest() throws JsonProcessingException {

        List<Ticket> tickets = getNotTakenTickets();

        ObjectMapper mapper = new ObjectMapper();
        String ticketsJson = mapper.writeValueAsString(tickets);

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "notTaken/between/2014-3-1/2014-3-3"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ticketsJson, MediaType.APPLICATION_JSON));

        ticketRestClient.getNotTakenBetweenDates("2014-3-1", "2014-3-3");
    }

    @Test
    public void getTicketsOfCustomerRestTest() throws JsonProcessingException {

        List<Ticket> tickets = getTicketsOfCustomer(1L);

        ObjectMapper mapper = new ObjectMapper();
        String ticketsJson = mapper.writeValueAsString(tickets);

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "ofCustomer/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ticketsJson, MediaType.APPLICATION_JSON));

        ticketRestClient.getTicketsOfCustomer(1L);
    }

    @Test
    public void getTicketSumOfCustomerRestTest() throws  JsonProcessingException {

        Integer totalCost = getTicketsSumOfCustomer().getTotalCost();

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "sum/ofCustomer/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("1500", MediaType.APPLICATION_JSON));

        Integer sum = ticketRestClient.getTicketSumOfCustomer(1L);
        assertEquals(sum, totalCost);
    }

    @Test
    public void updateTicketWhenCustomerRemovedRestTest() {

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "update/whenCustomerRemoved/1"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));
        ticketRestClient.updateTicketsWhenCustomerRemoved(1L);
    }

    @Test
    public void checkTicketExistenceRestTest() {

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "check/2014-03-01/title/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(Boolean.TRUE.toString(), MediaType.APPLICATION_JSON));

        ticketRestClient.checkTicketExistence(Date.valueOf("2014-3-1"), "title", 1L);
    }

    @Test
    public void checkTicketNonexistenceRestTest() {

        server.expect(requestTo(HOST + TICKET_ROOT_PATH + "check/2014-03-01/random%20title/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(Boolean.FALSE.toString(), MediaType.APPLICATION_JSON));

        ticketRestClient.checkTicketExistence(Date.valueOf("2014-3-1"), "random title", 1L);
    }

    public static class TicketDataFixture {

        public static Ticket getExistingTakenTicket(Long id) {

            Ticket ticket = new Ticket();
            ticket.setTicketId(id);
            ticket.setTitle("title" + id);
            ticket.setCustomerId(id);
            ticket.setDate(Date.valueOf("2014-03-01"));
            ticket.setTaken(Boolean.TRUE);
            ticket.setCost(1500);
            ticket.setLocation(1);
            return ticket;
        }

        public static Ticket getExistingNotTakenTicket(Long id) {

            Ticket ticket = new Ticket();
            ticket.setTicketId(id);
            ticket.setTitle("title" + id);
            ticket.setCustomerId(null);
            ticket.setDate(Date.valueOf("2014-03-01"));
            ticket.setTaken(Boolean.FALSE);
            ticket.setCost(1500);
            ticket.setLocation(1);
            return ticket;
        }

        public static List<Ticket> getAllTickets() {

            List<Ticket> tickets = new ArrayList<Ticket>();
            for (int i = 1; i <= 3; i++) {
                tickets.add(getExistingTakenTicket(Long.valueOf(i)));
            }
            return tickets;
        }

        public static List<Ticket> getNotTakenTickets() {

            List<Ticket> tickets = new ArrayList<Ticket>();
            for (int i = 1; i <= 3; i++) {
                tickets.add(getExistingNotTakenTicket(Long.valueOf(i)));
            }
            return tickets;
        }

        public static List<Ticket> getTicketsOfCustomer(Long customerId) {

            List<Ticket> tickets = new ArrayList<Ticket>();
            for (int i = 1; i <= 3; i++) {
                Ticket ticket = getExistingTakenTicket(Long.valueOf(i));
                ticket.setCustomerId(customerId);
                tickets.add(ticket);
            }
            return tickets;
        }

        public static TotalCustomerCost getTicketsSumOfCustomer() {

            TotalCustomerCost cost = new TotalCustomerCost();
            cost.setTotalCost(1500);
            return cost;
        }
    }
}
