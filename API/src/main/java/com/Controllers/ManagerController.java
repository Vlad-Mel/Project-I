package com.Controllers;

import com.DTOs.TicketStatusChangeDto;
import com.Database.TicketDAO;
import com.ErrorHandlers.ErrorMessage;
import com.Models.Ticket;
import com.Models.TicketStatus;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ManagerController {

    TicketDAO ticketDAO = new TicketDAO();
    
    public void updateTicketStatus(Context ctx) {
        
        TicketStatusChangeDto ticketStatusChangeDto = ctx.bodyAsClass(TicketStatusChangeDto.class);
        String status = ticketStatusChangeDto.status.toLowerCase();
        int id = ticketStatusChangeDto.id;

        /* Check if the ticket with the id exists */
        Ticket ticket = ticketDAO.findATicket(id);
        if( ticket == null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorMessage("Ticket with such id does not exist."));
            return;
        }

        System.out.println(ticket.getStatus().equals("approved"));

        /* If the decision has been made, we cannot change it. */
        if (ticket.getStatus().equals("approved") || ticket.getStatus().equals("denied")) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorMessage("The decision has already made about case #" + ticket.getId() + " and cannot be changed."));
            return;
        }
     
        try {
            TicketStatus ticketStatus;
            switch (status) {
                case "approved": ticketStatus = TicketStatus.APPROVED;
                                 break;
                case "denied": ticketStatus = TicketStatus.DENIED;
                                 break;
                default:
                    ctx.status(HttpStatus.BAD_REQUEST);
                    ctx.json(new ErrorMessage("Invalid status. You can change status only to \"Approved\" or \"Denied\"."));
                    return;
            }
            ticketDAO.updateStatusTicket(ticketStatus, id);

            ctx.json(ticketDAO.allTicketsWhere("pending"));
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Unresolved error."));
        }
    }

    public void promoteToManager(Context ctx) {

    }

    public void demoteAnEmployee(Context ctx) {

    }
}
