package com.DTOs;

public class TicketDBDto {
    public String status;
    public String author;
    public String datePublished;
    public double amount;
    public String description;

    public TicketDBDto(String status, String author, String datePublished, double amount, String description) {
        this.status = status;
        this.author = author;
        this.datePublished = datePublished;
        this.amount = amount;
        this.description = description;
    }
    
}
