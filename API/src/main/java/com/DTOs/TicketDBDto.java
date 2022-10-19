package com.DTOs;

public class TicketDBDto {
    public int id;
    public String status;
    public String author;
    public String datePublished;
    public double amount;
    public String description;

    public TicketDBDto(int id, String status, String author, String datePublished, double amount, String description) {
        this.id = id;
        this.status = status;
        this.author = author;
        this.datePublished = datePublished;
        this.amount = amount;
        this.description = description;
    }
    
}
