package com.Models;

import com.DTOs.EmployeeDBDto;

public class Manager extends Employee {

    final public String role;

    public String getRole() { return role; }

    public Manager(String username, String password) {
        super(username, password);
        this.role = "Manager";
    }

    public Manager(EmployeeDBDto employeeDBDto) {
        super(employeeDBDto);
        this.role = "Manager";
    }

}
