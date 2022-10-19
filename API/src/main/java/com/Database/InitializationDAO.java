package com.Database;

import  java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.Models.Employee;
import com.Models.Manager;
import com.Models.Ticket;
import com.Utilities.ConnectionFactory;

public class InitializationDAO extends ConnectionFactory {

    public void initializeTables() {
        Connection connection = establishConnection();
        Statement stmt = null;
        String employeeSQL = "CREATE TABLE IF NOT EXISTS employee (" +
            "employee_id SERIAL PRIMARY KEY, " +
            "employee_login VARCHAR(50) UNIQUE NOT NULL, " +
            "employee_encryptedPassword TEXT NOT NULL, " +
            "employee_passwordSalt BYTEA NOT NULL, " +
            "employee_role VARCHAR(20) NOT NULL" +
        ")";
        String reimbursementSQL = "CREATE TABLE IF NOT EXISTS reimbursement (" +
            "reimbursement_id SERIAL PRIMARY KEY," +
            "reimbursement_status VARCHAR(20) NOT NULL," +
            "reimbursement_author VARCHAR(50) NOT NULL REFERENCES employee(employee_login)," +
            "reimbursement_description TEXT NOT NULL, " +
            "reimbursement_date VARCHAR(30) NOT NULL, " +
            "reimbursement_amount NUMERIC NOT NULL " +
        ")";
     
        try {
            stmt = connection.createStatement();
            stmt.execute(employeeSQL + "; " + reimbursementSQL);
        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try { connection.close(); stmt.close(); } 
            catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void populateTables() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        TicketDAO ticketDAO = new TicketDAO();

        if (employeeDAO.allEmployees().isEmpty()) {
 
        employeeDAO.addEmployee(new Manager("admin", "admin"));
        employeeDAO.addEmployee(new Employee("Isaac Newton", "qwerty"));
        employeeDAO.addEmployee(new Employee("Elon Musk", "qwerty"));
        employeeDAO.addEmployee(new Employee("William Thomson aka Lord Kelvin", "qwerty"));
        employeeDAO.addEmployee(new Employee("Volodymyr Melnychenko", "qwerty"));
        employeeDAO.addEmployee(new Employee("Albert Einstein", "qwerty"));
        employeeDAO.addEmployee(new Employee("Max Planck", "qwerty"));
        employeeDAO.addEmployee(new Employee("Werner Heisenberg", "qwerty"));
        employeeDAO.addEmployee(new Manager("Stephen Hawking", "qwerty"));
        employeeDAO.addEmployee(new Employee("John Doe", "qwerty"));
        employeeDAO.addEmployee(new Employee("John Lennon", "qwerty"));
        employeeDAO.addEmployee(new Employee("Paul McCartney", "qwerty"));



        ticketDAO.addTicket(new Ticket("Isaac Newton", "Bag of apples", 2.5));
        ticketDAO.addTicket(new Ticket("Isaac Newton", "A new castle.", 5_333_992.20));
        ticketDAO.addTicket(new Ticket("Elon Musk", "A New Gigafactory", 6_232_341_312.34));
        ticketDAO.addTicket(new Ticket("Elon Musk", "I do/don't want to buy Twitter", 44_399_999_999.34));
        ticketDAO.addTicket(new Ticket("Elon Musk", "Starlink Software Update", 125_499_999.00));
        ticketDAO.addTicket(new Ticket("Albert Einstein", "A New Violin", 1299.99));
        ticketDAO.addTicket(new Ticket("Volodymyr Melnychenko", "A New Laptop for working need", 700.49));
        ticketDAO.addTicket(new Ticket("Volodymyr Melnychenko", "IPhone 14. Not for the job, but... why not?", 1299.99));
        ticketDAO.addTicket(new Ticket("Werner Heisenberg", "Development of Heisenberg Uncertainty Principle", 444_323_215.99));
        }
    
    }
    
}
