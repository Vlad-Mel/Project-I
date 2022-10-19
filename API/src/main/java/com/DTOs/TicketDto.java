package com.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDto {
    public String description;
    public double amount;

    public TicketDto() {};

    public TicketDto(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    
}
