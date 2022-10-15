package com.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.DTOs.TicketDBDto;
import com.Models.Ticket;
import com.utility.ConnectionFactory;

public class TicketDAO extends ConnectionFactory {
 
    public boolean addTicket(Ticket ticket) {
        Connection connection = establishConnection();
        PreparedStatement addTicket = null;

        try {
            addTicket = connection.prepareStatement("INSERT INTO reimbursement VALUES (DEFAULT, ?, ?, ?, ?, ?)");

            addTicket.setString(1, ticket.getStatus());
            addTicket.setString(2, ticket.getAuthor());
            addTicket.setString(3, ticket.getDescription());
            addTicket.setString(4, ticket.getDatePublished());
            addTicket.setDouble(5, ticket.getAmount());

            addTicket.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); addTicket.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }
        return false;

    }


    public LinkedList<Ticket> allTickets () {
        Connection connection = establishConnection();
        LinkedList<Ticket> tickets = new LinkedList<>();
        Statement stmt = null;
        ResultSet set = null;
        
        try {
            stmt = establishConnection().createStatement();
            set = stmt.executeQuery(
                "SELECT reimbursement_author, reimbursement_description, reimbursement_status, reimbursement_amount, reimbursement_date, reimbursement_amount FROM reimbursement"
            );
            
            while (set.next()) {
                TicketDBDto ticketDBDto = new TicketDBDto(
                    set.getString("reimbursement_status"),
                    set.getString("reimbursement_author"),
                    set.getString("reimbursement_date"),
                    set.getDouble("reimbursement_amount"),
                    set.getString("reimbursement_description")
                );
                
                tickets.add(new Ticket(ticketDBDto));
            }
            return tickets;
            
        } catch (SQLException e) { e.printStackTrace(); } 
        finally {
            try { connection.close(); stmt.close(); set.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }

        return null;
    }

    
}
