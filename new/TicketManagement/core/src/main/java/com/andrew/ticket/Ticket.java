package com.andrew.ticket;

import com.andrew.customer.Customer;

/**
 * Created by andrew on 15.11.14.
 */
public class Ticket {

    private Long ticketId;
    private Integer cost;
    private Integer location;
    private String date;
    private String title;
    private boolean taken;
    private Customer customer;

    public boolean isTaken() {
        return taken;
    }

    public void setTaken() {

        taken = true;
    }

    public void setNotTaken() {

        taken = false;
    }

    public Long getTicketId() {

        return ticketId;
    }

    public void setTicketId(Long ticketId) {

        this.ticketId = ticketId;
    }

    public Integer getCost() {

        return cost;
    }

    public void setCost(Integer cost) {

        this.cost = cost;
    }

    public Integer getLocation() {

        return location;
    }

    public void setLocation(Integer location) {

        this.location = location;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public void deleteCustomer() {

        this.customer = null;
    }
}