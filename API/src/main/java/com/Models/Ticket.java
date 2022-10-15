package com.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.DTOs.TicketDBDto;
import com.DTOs.TicketDto;

public class Ticket {

    private String status;
    private String author;
    private String datePublished;
    private double amount;

    public String description;


    public Ticket(String author, String description, double amount) {
        this.status = "pending";
        this.datePublished  = new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());

        this.author = author;
        this.description = description;
        this.amount = amount;
    }

    public Ticket(Employee employee, String description, double amount) { this(employee.getLogin(), description, amount); }

    public Ticket(TicketDto ticketDto, String author) {this(author, ticketDto.description, ticketDto.amount);}

    public Ticket(TicketDBDto ticket) {
        this.status = ticket.status;
        this.author = ticket.author;
        this.datePublished = ticket.datePublished;
        this.amount = ticket.amount;
        this.description = ticket.description;
    }


    public String getStatus() { return status; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getDatePublished() { return datePublished; }
    public double getAmount() { return amount; }

    
    public void setStatus(String status) {
        String lowerCaseStatus = status.toLowerCase();

        if (lowerCaseStatus != "approved" || lowerCaseStatus != "denied") 
            throw new RuntimeException("The status can be approved or denied");
        
        this.status = lowerCaseStatus;
    }
    
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Ticket [status=" + status + ", author=" + author + ", datePublished=" + datePublished + ", amount="
                + amount + ", description=" + description + "]";
    }


    
}
