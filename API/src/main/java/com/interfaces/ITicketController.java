package com.interfaces;

import io.javalin.http.Context;

public interface ITicketController {

    public void createTicket(Context ctx);

    public void getAllTickets(Context ctx);

    public void getEmployeeTickets (Context ctx);
    
}
