package com.Models;

public enum TicketStatus {
    PENDING("pending"),
    APPROVED("approved"),
    DENIED("denied");

    private final String status;

    private TicketStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    } 
}