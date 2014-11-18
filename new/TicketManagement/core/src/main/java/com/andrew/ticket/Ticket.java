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

    @Override
    public boolean equals(Object obj) {

        if ( !(obj instanceof Ticket)) {
            return false;
        }

        Ticket ticket = (Ticket) obj;
        return ticketId.equals(ticket.ticketId) &&
               cost.equals(ticket.cost) &&
               location.equals(ticket.location) &&
               date.equals(ticket.date) &&
               title.equals(ticket.title) &&
               taken == ticket.taken &&
               customer.equals(ticket.customer);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("ticketId=").append(ticketId)
          .append(", cost=").append(cost)
          .append(", location=").append(location)
          .append(", date='").append(date).append('\'')
          .append(", title='").append(title).append('\'')
          .append(", taken=").append(taken)
          .append(", customer=").append(customer.getCustomerId()).append('}');

        return sb.toString();
    }
}