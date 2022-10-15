package com.Controllers;

import com.DTOs.TicketDto;
import com.Database.EmployeeDAO;
import com.Database.TicketDAO;
import com.Models.Employee;
import com.Models.Ticket;
import com.Services.TicketService;
import com.errorHandlers.ErrorMessage;
import com.interfaces.ITicketController;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class TicketController implements ITicketController {
    TicketDAO ticketDAO = new TicketDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public void createTicket(Context ctx) {
        try {
            int employeeId = Integer.parseInt(ctx.pathParam("id"));

            TicketDto ticketDto = ctx.bodyAsClass(TicketDto.class);
            Employee employee = employeeDAO.findAnEmployee(employeeId);

            if (employee == null) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorMessage("User does not exist."));
                return;
            }
            String author = employee.getLogin();

            Ticket ticket = new Ticket(ticketDto, author);
            ticketDAO.addTicket(ticket);

            ctx.status(HttpStatus.CREATED);
            ctx.json(ticketDto);
        } catch (Exception e) {
            System.err.println(e);
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Unresolved error."));
        }
    }

    public void getAllTickets(Context ctx) {
        ctx.json(ticketDAO.allTickets());
    }

    public void getEmployeeTickets(Context ctx) {
        TicketService ticketService = new TicketService();

        try {
            int employeeId = Integer.parseInt(ctx.pathParam("id"));

            ctx.json(ticketService.allEmployeeTickets(employeeId));
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Unresolved error."));
        }
    }
    
}
