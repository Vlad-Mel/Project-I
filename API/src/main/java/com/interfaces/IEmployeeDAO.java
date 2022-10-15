package com.interfaces;

import java.util.LinkedList;

import com.Models.Employee;

public interface IEmployeeDAO {
    
    public LinkedList<Employee> allEmployees();

    public Employee findAnEmployee(String login);
    public Employee findAnEmployee(int id);

    public boolean addEmployee(Employee employee);
}
