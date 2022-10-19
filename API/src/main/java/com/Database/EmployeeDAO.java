package com.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.DTOs.EmployeeDBDto;
import com.Interfaces.IEmployeeDAO;
import com.Models.Employee;
import com.Models.Manager;
import com.Utilities.ConnectionFactory;


public class EmployeeDAO extends ConnectionFactory implements IEmployeeDAO  {

    public LinkedList<Employee> allEmployees() {
        Connection connection = establishConnection();
        LinkedList<Employee> employees = new LinkedList<>();
        Statement stmt = null;
        ResultSet set = null;
        
        try {
            stmt = establishConnection().createStatement();
            set = stmt.executeQuery(
                "SELECT employee_id, employee_login, employee_encryptedPassword, employee_passwordSalt, employee_role FROM employee"
            );
            
            while (set.next()) {
                EmployeeDBDto employeeDBDto = new EmployeeDBDto(
                    set.getInt("employee_id"),
                    set.getString("employee_login"),
                    set.getString("employee_encryptedPassword"),
                    set.getBytes("employee_passwordSalt"),
                    set.getString("employee_role")
                );
                
                employees.add(employeeDBDto.role.equals("Employee") ? new Employee(employeeDBDto) : new Manager(employeeDBDto));
            }
            return employees;
            
        } catch (SQLException e) { e.printStackTrace(); } 
        finally {
            try { connection.close(); stmt.close(); set.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }

        return null;
    }

    /**
     * Function that finds specific employee by its ID credential. The response hides the ID. 
     * Return NULL if the user is not found with such id.
     * 
     * @return Employee | null
     */
    public Employee findAnEmployee(int id) {
       
        String SQL = "SELECT employee_id, employee_login, employee_encryptedPassword, employee_passwordSalt, employee_role " +
                        "FROM employee "+
                        "WHERE employee_id = ?";
        Connection connection = establishConnection();
        PreparedStatement findEmployee = null;
        ResultSet set = null;
            
        try {
            findEmployee = connection.prepareStatement(SQL);
            findEmployee.setInt(1, id);
            set = findEmployee.executeQuery(); 

            if (set.next()) {
                EmployeeDBDto employeeDBDto = new EmployeeDBDto(
                        set.getInt("employee_id"),
                        set.getString("employee_login"),
                        set.getString("employee_encryptedPassword"),
                        set.getBytes("employee_passwordSalt"),
                        set.getString("employee_role")
                );

                return employeeDBDto.role.equals("Employee") ? new Employee(employeeDBDto) : new Manager(employeeDBDto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try { findEmployee.close(); set.close(); connection.close(); } 
            catch (SQLException e) { e.printStackTrace();}
        }

        return null;
    }

    /**
     * Function that finds specific employee by its Login credential. The response hides the ID. 
     * Return NULL if the user is not found with such login.
     * 
     * @return Employee | null
     */
    public Employee findAnEmployee(String login) {

        String SQL = "SELECT employee_id, employee_login, employee_encryptedPassword, employee_passwordSalt, employee_role " +
                        "FROM employee "+
                        "WHERE lower(employee_login) = lower(?)";
        Connection connection = establishConnection();
        PreparedStatement findEmployee = null;
        ResultSet set = null;
            
        try {
            findEmployee = connection.prepareStatement(SQL);
            findEmployee.setString(1, login);
            set = findEmployee.executeQuery(); 

            if (set.next()) {
                EmployeeDBDto employeeDBDto = new EmployeeDBDto(
                        set.getInt("employee_id"),
                        set.getString("employee_login"),
                        set.getString("employee_encryptedPassword"),
                        set.getBytes("employee_passwordSalt"),
                        set.getString("employee_role")
                );

                return employeeDBDto.role.equals("Employee") ? new Employee(employeeDBDto) : new Manager(employeeDBDto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try { findEmployee.close(); set.close(); connection.close(); } 
            catch (SQLException e) { e.printStackTrace();}
        }

        return null;
    }

    


    /**
     * Adds new employee to database. All fields employee's fields must to be populated. Cannot be used for update. 
     * Returns true if operation was done successfully.
     * @return boolean
     */
    public boolean addEmployee(Employee employee) {
        Connection connection = establishConnection();
        PreparedStatement addEmployee = null;
    
        try {
            addEmployee = connection.prepareStatement("INSERT INTO employee VALUES (DEFAULT, ?, ?, ?, ?)");

          
            addEmployee.setString(1, employee.getLogin());
            addEmployee.setString(2, employee.getEncryptedPassword());
            addEmployee.setBytes(3,  employee.getPasswordSalt());
            addEmployee.setString(4, employee.getRole());

            addEmployee.execute();
            return true;
            
        } catch (SQLException e) { e.printStackTrace(); } 
        finally {
            try { connection.close(); addEmployee.close(); } 
            catch (SQLException e1) { e1.printStackTrace(); }
        }
        return false;

    }

    /**
     * 
     */
    public boolean deleteEmployee(int id) {
        Connection connection = establishConnection();
        PreparedStatement deleteEmployee = null;

        try {
            deleteEmployee = connection.prepareStatement("DELETE FROM employee WHERE id = ?");
            deleteEmployee.setInt(1, id);
            deleteEmployee.execute(); 
        } catch (SQLException e) {

        }




        return false;
    }


}
