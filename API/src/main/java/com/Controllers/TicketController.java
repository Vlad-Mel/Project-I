package com.Controllers;

import java.util.LinkedList;

import com.DTOs.TicketDto;
import com.Database.EmployeeDAO;
import com.Database.TicketDAO;
import com.ErrorHandlers.ErrorMessage;
import com.Interfaces.ITicketController;
import com.Models.Employee;
import com.Models.Ticket;
import com.Services.TicketService;

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
            
            if (ticketDto.amount <= 0 ) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorMessage("The reimbursement cost cannot be less or equal to 0."));
                return;
            }

            if (ticketDto.description.length() < 5 ) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorMessage("The description is too short. It can be at least 5 symbols of length."));
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

    public void getAllPendingTickets(Context ctx) {
        LinkedList<Ticket> tickets = ticketDAO.allTicketsWhere("pending");
        if (!tickets.isEmpty()) {
            ctx.status(HttpStatus.OK);
            ctx.json(tickets);
            return;
        }
        ctx.json(new ErrorMessage("No more pending request tickets left", "notification"));
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
