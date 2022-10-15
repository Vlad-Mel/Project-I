package com.Models;

import java.util.Arrays;

import com.DTOs.EmployeeDBDto;
import com.DTOs.EmployeeDto;
import com.Services.PasswordService;

public class Employee {
 
    private String login;
    private String encryptedPassword;
    private byte[] passwordSalt;

    final public String role = "Employee";


    public Employee(EmployeeDBDto employee) {
        this.login = employee.login;
        this.encryptedPassword = employee.encryptedPassword;
        this.passwordSalt = employee.passwordSalt;
    }

    public Employee(EmployeeDto employeeDto)  {
        this(employeeDto.login, employeeDto.password);
    }

    public Employee(String username, String password)  {
        this.passwordSalt = PasswordService.generatePasswordSalt();
        this.login = username;
        setEncryptedPassword(password);
    }


    public void setEncryptedPassword(String password) {
        this.encryptedPassword = PasswordService.generateEncryptedPassword(password, passwordSalt);
    }


    public byte[] getPasswordSalt() { return passwordSalt; }
    public String getLogin() { return login; }
    public String getRole() { return role; }
    public String getEncryptedPassword() { return encryptedPassword; }

    @Override
    public String toString() {
        return "Employee [login=" + login + ", encryptedPassword=" + encryptedPassword + ", passwordSalt="
                + Arrays.toString(passwordSalt) + ", role=" + role + "]";
    }
}
