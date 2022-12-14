package com.Controllers;

import com.DTOs.TicketStatusChangeDto;
import com.DTOs.UpdateEmployeeRoleDto;
import com.Database.EmployeeDAO;
import com.Database.TicketDAO;
import com.ErrorHandlers.MessageResponse;
import com.Models.Employee;
import com.Models.Ticket;
import com.Models.TicketStatus;
import com.Utilities.JWToken;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ManagerController {

    TicketDAO ticketDAO = new TicketDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    
    public void updateTicketStatus(Context ctx) {
        
        TicketStatusChangeDto ticketStatusChangeDto = ctx.bodyAsClass(TicketStatusChangeDto.class);
        String status = ticketStatusChangeDto.status.toLowerCase();
        int id = ticketStatusChangeDto.id;

        /* Check if the ticket with the id exists */
        Ticket ticket = ticketDAO.findATicket(id);
        if( ticket == null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new MessageResponse("Ticket with such id does not exist."));
            return;
        }

        System.out.println(ticket.getStatus().equals("approved"));

        /* If the decision has been made, we cannot change it. */
        if (ticket.getStatus().equals("approved") || ticket.getStatus().equals("denied")) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new MessageResponse("The decision has already made about case #" + ticket.getId() + " and cannot be changed."));
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
                    ctx.json(new MessageResponse("Invalid status. You can change status only to \"Approved\" or \"Denied\"."));
                    return;
            }
            ticketDAO.updateStatusTicket(ticketStatus, id);

            ctx.json(ticketDAO.allTicketsWhere("pending"));
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new MessageResponse("Unresolved error."));
        }
    }

    public void promoteToManager(Context ctx) {
        UpdateEmployeeRoleDto updateEmployee = ctx.bodyAsClass(UpdateEmployeeRoleDto.class);
        Employee manager = JWToken.extractEmployeeFromToken(ctx.cookie("token"));
        Employee employee = employeeDAO.findAnEmployee(updateEmployee.id);
        String role = updateEmployee.role.toLowerCase();

        if (employee == null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new MessageResponse("User with ID #" + updateEmployee.id + " does not exist."));
            return;
        }

        switch(role) {
            case "manager": {
                if (employee.getRole().equals("Manager")) {
                    ctx.json(new MessageResponse("The employee is already manager"));
                    ctx.status(HttpStatus.BAD_REQUEST);
                    return;
                }

                if (manager.getId() == employee.getId()) {
                    ctx.json(new MessageResponse("You cannot demote yourself. Ask someone else."));
                    ctx.status(HttpStatus.BAD_REQUEST);
                    return;
                }

                employeeDAO.updateEmployeeRole(updateEmployee.id, "Manager");
                ctx.json(new MessageResponse("The employee " + employee.getLogin() + " is now manager!", "news"));
            };
            return;
            case "employee": {
                if (employee.getRole().equals("Employee")) {
                    ctx.json(new MessageResponse("The employee is already employee"));
                    ctx.status(HttpStatus.BAD_REQUEST);
                    return;
                }

                employeeDAO.updateEmployeeRole(updateEmployee.id, "Employee");
                ctx.json(new MessageResponse("The employee " + employee.getLogin() + " is now employee!", "news"));
            }
            return;
            default:
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new MessageResponse("Invalid value for the status. The employee can be either employee or manager."));
        }
    }
}
