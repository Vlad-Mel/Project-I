package com.Models;

import java.util.Arrays;

import com.DTOs.EmployeeDBDto;
import com.DTOs.EmployeeDto;
import com.Utilities.PasswordUtility;

public class Employee {
 
    private int id = 0;
    private String login;
    private String encryptedPassword;
    private byte[] passwordSalt;

    final public String role = "Employee";


    public Employee(EmployeeDBDto employee) {
        this.id = employee.id;
        this.login = employee.login;
        this.encryptedPassword = employee.encryptedPassword;
        this.passwordSalt = employee.passwordSalt;
    }

    public Employee(EmployeeDto employeeDto)  {
        this(employeeDto.login, employeeDto.password);
    }

    public Employee(String username, String password)  {
        this.passwordSalt = PasswordUtility.generatePasswordSalt();
        this.login = username;
        setEncryptedPassword(password);
    }


    public void setEncryptedPassword(String password) {
        this.encryptedPassword = PasswordUtility.generateEncryptedPassword(password, passwordSalt);
    }

    public int getId() { return this.id; }
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
