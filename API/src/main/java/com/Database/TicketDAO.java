package com.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.DTOs.TicketDBDto;
import com.Models.Ticket;
import com.Models.TicketStatus;
import com.Utilities.ConnectionFactory;

public class TicketDAO extends ConnectionFactory {
 
    /**
     * Adds a ticket to the database.
     * @return boolean
     */
    public boolean addTicket(Ticket ticket) {
        Connection connection = establishConnection();
        PreparedStatement addTicket = null;

        try {
            addTicket = connection.prepareStatement(
                "INSERT INTO reimbursement VALUES (DEFAULT, ?, ?, ?, ?, ?)"
            );

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

    /**
     * The function updates a ticket's status only. Requires TicketStatus in order to safely update to one of 3 acceptable values for reimbursement_status field.
     * @param TicketStatus, int
     * @return boolean
     */
    public boolean updateStatusTicket(TicketStatus ticketStatus, int id) {
        Connection connection = establishConnection();
        PreparedStatement updateStatusTicket = null;

        try {
            updateStatusTicket = connection.prepareStatement(
                "UPDATE reimbursement SET reimbursement_status = ? WHERE reimbursement_id = ?"
            );

            updateStatusTicket.setString(1, ticketStatus.getStatus());
            updateStatusTicket.setInt(2, id);

            updateStatusTicket.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); updateStatusTicket.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }
        return false;

    }


    public LinkedList<Ticket> allTickets () {
        Connection connection = establishConnection();
        LinkedList<Ticket> tickets = new LinkedList<>();
        String SQL = "SELECT reimbursement_id, reimbursement_author, reimbursement_description, " + 
                     "reimbursement_status, reimbursement_amount, reimbursement_date, reimbursement_amount " + 
                     "FROM reimbursement";
        Statement stmt = null;
        ResultSet set = null;
        
        try {
            stmt = establishConnection().createStatement();
            set = stmt.executeQuery(SQL);
            
            while (set.next()) {
                TicketDBDto ticketDBDto = new TicketDBDto(
                    set.getInt("reimbursement_id"),
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

    public LinkedList<Ticket> allTicketsWhere (String value) {
        Connection connection = establishConnection();
        LinkedList<Ticket> tickets = new LinkedList<>();
        String SQL = "SELECT reimbursement_id, reimbursement_author, reimbursement_description, " + 
                     "reimbursement_status, reimbursement_amount, reimbursement_date, reimbursement_amount " + 
                     "FROM reimbursement " +
                     "WHERE reimbursement_status = ?";
        PreparedStatement stmt = null;
        ResultSet set = null;
        
        try {
            stmt = establishConnection().prepareStatement(SQL);
            stmt.setString(1, value);
            set = stmt.executeQuery();
            
            while (set.next()) {
                TicketDBDto ticketDBDto = new TicketDBDto(
                    set.getInt("reimbursement_id"),
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


    public Ticket findATicket(int id) {
        String SQL = "SELECT reimbursement_id, reimbursement_author, reimbursement_description, " + 
                            "reimbursement_status, reimbursement_amount, reimbursement_date, reimbursement_amount " + 
                            "FROM reimbursement WHERE reimbursement_id = ?";
        Connection connection = establishConnection();
        PreparedStatement findATicket = null;
        ResultSet set = null;

        try {
            findATicket = connection.prepareStatement(SQL);
            findATicket.setInt(1, id);
            set = findATicket.executeQuery();

            if (set.next()) { 
                TicketDBDto ticketDBDto = new TicketDBDto(
                    set.getInt("reimbursement_id"),
                    set.getString("reimbursement_status"),
                    set.getString("reimbursement_author"),
                    set.getString("reimbursement_date"),
                    set.getDouble("reimbursement_amount"),
                    set.getString("reimbursement_description")
                );
                return new Ticket(ticketDBDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); findATicket.close(); set.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }

        return null;
    }
    
}
