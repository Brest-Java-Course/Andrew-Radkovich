package com.andrew.ticket;

import com.andrew.customer.Customer;

import java.sql.Date;

/**
 * Created by andrew on 15.11.14.
 */
public class Ticket {

    private Long ticketId;
    private Integer cost;
    private Integer location;
    private Date date;
    private String title;
    private Boolean taken;
    private Long customerId;

    public Long getTicketId() {

        return ticketId;
    }

    public void setTicketId(Long ticketId) {

        this.ticketId = ticketId;
    }

    public Boolean isTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
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

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Long getCustomerId() {

        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public void deleteCustomer() {

        this.customerId = null;
    }

    @Override
    public boolean equals(Object obj) {

        if ( this == obj) {
            return true;
        }

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
               customerId.equals(ticket.customerId);
    }

    @Override
    public int hashCode() {

        int result = ticketId.hashCode();
        result = 31 * result + cost.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + taken.hashCode();
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
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
          .append(", customerId=").append(customerId).append('}');

        return sb.toString();
    }
}