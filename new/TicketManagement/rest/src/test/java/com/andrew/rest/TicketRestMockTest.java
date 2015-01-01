package com.andrew.rest;

import com.andrew.TotalCost.TotalCustomerCost;
import com.andrew.service.CustomerService;
import com.andrew.service.TicketService;
import com.andrew.service.exception.InvalidDateException;
import com.andrew.ticket.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static com.andrew.rest.TicketRestMockTest.TicketDataFixture.*;

/**
 * Created by andrew on 18.12.14.
 */
public class TicketRestMockTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TicketRestController ticketRestController;

    @Mock
    private TicketService ticketService;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(ticketRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getTicketByIdRestTestMockTest() throws Exception {

        Ticket ticket = getExistingTakenTicket(1L);
        when(ticketService.selectTicketById(1L)).thenReturn(ticket);
        mockMvc.perform(get("/rest/ticket/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"ticketId\":1,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title1\",\"taken\":true,\"customerId\":1}"));

        verify(ticketService).selectTicketById(1L);
    }

    @Test
    public void getAllTicketsMockTest() throws Exception {

        List<Ticket> expectedTickets = TicketDataFixture.getAllTickets();
        when(ticketService.selectAllTickets()).thenReturn(expectedTickets);
        mockMvc.perform(get("/rest/ticket/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"ticketId\":1,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title1\",\"taken\":true,\"customerId\":1}," +
                        "{\"ticketId\":2,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title2\",\"taken\":true,\"customerId\":2}," +
                        "{\"ticketId\":3,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title3\",\"taken\":true,\"customerId\":3}]"
                ));
        verify(ticketService).selectAllTickets();
    }

    @Test
    public void getTicketsOfCustomerMockTest() throws Exception {

        List<Ticket> expectedTicketsOfCustomer = TicketDataFixture.getTicketsOfCustomer(1L);
        when(ticketService.getTicketsOfCustomer(1L)).thenReturn(expectedTicketsOfCustomer);
        mockMvc.perform(get("/rest/ticket/ofCustomer/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"ticketId\":1,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title1\",\"taken\":true,\"customerId\":1}," +
                        "{\"ticketId\":2,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title2\",\"taken\":true,\"customerId\":1}," +
                        "{\"ticketId\":3,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title3\",\"taken\":true,\"customerId\":1}]"
                ));
        verify(ticketService).getTicketsOfCustomer(1L);
    }

    @Test
    public void getTicketSumOfCustomerMockTest() throws Exception {

        TotalCustomerCost cost = TicketDataFixture.getTicketsSumOfCustomer();
        when(ticketService.getTicketsSumOfCustomer(1L)).thenReturn(cost);
        mockMvc.perform(get("/rest/ticket/sum/ofCustomer/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "1500"
                ));
        verify(ticketService).getTicketsSumOfCustomer(1L);
    }

    @Test
    public void getNotTakenTicketsBetweenDatesMockTest() throws  Exception {

        List<Ticket> tickets = TicketDataFixture.getNotTakenTickets();
        when(ticketService.selectNotTakenBetweenDates("2014-3-1", "2014-3-2")).thenReturn(tickets);
        mockMvc.perform(get("/rest/ticket/notTaken/between/2014-3-1/2014-3-2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"ticketId\":1,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title1\",\"taken\":false,\"customerId\":null}," +
                                "{\"ticketId\":2,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title2\",\"taken\":false,\"customerId\":null}," +
                                "{\"ticketId\":3,\"cost\":1500,\"location\":1,\"date\":\"2014-03-01\",\"title\":\"title3\",\"taken\":false,\"customerId\":null}]"
                ));
        verify(ticketService).selectNotTakenBetweenDates("2014-3-1", "2014-3-2");
    }

    @Test
    public void getNotTakenTicketsBetweenWrongDatesMockTest() throws  Exception {

        List<Ticket> tickets = TicketDataFixture.getNotTakenTickets();
        when(ticketService.selectNotTakenBetweenDates("2014-33-1", "2014-3-2"))
                .thenThrow(new InvalidDateException("error parsing dates", "2014-33-1 2014-3-2"));

        mockMvc.perform(get("/rest/ticket/notTaken/between/2014-33-1/2014-3-2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(ticketService).selectNotTakenBetweenDates("2014-33-1", "2014-3-2");
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
            for(int i = 1; i <= 3; i++) {
                tickets.add(getExistingTakenTicket(Long.valueOf(i)));
            }
            return tickets;
        }

        public static List<Ticket> getNotTakenTickets() {

            List<Ticket> tickets = new ArrayList<Ticket>();
            for(int i = 1; i <= 3; i++) {
                tickets.add(getExistingNotTakenTicket(Long.valueOf(i)));
            }
            return tickets;
        }

        public static List<Ticket> getTicketsOfCustomer(Long customerId) {

            List<Ticket> tickets = new ArrayList<Ticket>();
            for(int i = 1; i <= 3; i++) {
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
