package com.Services;

import java.util.LinkedList;

import com.Database.EmployeeDAO;
import com.Database.TicketDAO;
import com.Models.Employee;
import com.Models.Ticket;

public class TicketService {

    public LinkedList<Ticket> allEmployeeTickets(int id) {

        /* Makes 2 requests to database and iterate on server-side. Extremely inefficient.s */
        LinkedList<Ticket> tickets = new TicketDAO().allTickets();
        Employee employee = new EmployeeDAO().findAnEmployee(id);

        if (employee == null) return new LinkedList<Ticket>();

        tickets.removeIf( ticket -> !ticket.getAuthor().equals(employee.getLogin()));
        return tickets;
    }

}
