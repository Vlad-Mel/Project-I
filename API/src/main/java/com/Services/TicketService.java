package com.Services;

import java.util.Deque;
import java.util.LinkedList;

import com.Database.EmployeeDAO;
import com.Database.TicketDAO;
import com.Models.Employee;
import com.Models.Ticket;

public class TicketService {

    public LinkedList<Ticket> allEmployeeTickets(int id) {

        /* Makes 2 requests to database and iterate on server-side. Extremely inefficiently. */
        LinkedList<Ticket> tickets = new TicketDAO().allTickets();
        Employee employee = new EmployeeDAO().findAnEmployee(id);

        /* Return empty LinkedList if the user does not exist */
        if (employee == null) return new LinkedList<Ticket>();

        /* Remove all tickets where authors name and login do not match. */
        tickets.removeIf( ticket -> !ticket.getAuthor().equals(employee.getLogin()));
        return tickets;
    }

}
