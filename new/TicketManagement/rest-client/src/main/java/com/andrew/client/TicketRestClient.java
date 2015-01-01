package com.andrew.client;

import com.andrew.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 28.12.14.
 */
public class TicketRestClient {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TICKET_ROOT_PATH = "/rest/ticket/";

    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TicketRestClient(String host) {

        this.host = host;
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    public Ticket getTicketById(Long id) {

        return restTemplate.getForObject(host + TICKET_ROOT_PATH + id, Ticket.class);
    }

    public Ticket[] getNotTakenTickets() {

        return restTemplate.getForObject(host + TICKET_ROOT_PATH + "notTaken", Ticket[].class);
    }

    public Ticket[] getNotTakenBetweenDates(String dateLow, String dateHigh) {

        return restTemplate.getForObject(host + TICKET_ROOT_PATH + "notTaken/between/" + dateLow + "/" + dateHigh, Ticket[].class);
    }

    public Ticket[] getTicketsOfCustomer(Long customerId) {

        return restTemplate.getForObject(host + TICKET_ROOT_PATH + "ofCustomer/" + customerId, Ticket[].class);
    }

    public Integer getTicketSumOfCustomer(Long customerId) {

        return restTemplate.getForObject(host + TICKET_ROOT_PATH + "sum/ofCustomer/" + customerId, Integer.class);
    }


}
