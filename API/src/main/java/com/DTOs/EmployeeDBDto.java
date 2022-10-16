package com.DTOs;

import java.util.Arrays;

import com.Models.Employee;

public class EmployeeDBDto {
    public int id;
    public String login;
    public String encryptedPassword;
    public byte[] passwordSalt;
    public String role;

    public EmployeeDBDto () {};

    public EmployeeDBDto(int id, String login, String encryptedPassword, byte[] passwordSalt, String role) {
        this.id = id;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
        this.passwordSalt = passwordSalt;
        this.role = role;
    }
    
    public EmployeeDBDto(Employee employee) {
        this.id = employee.getId();
        this.login = employee.getLogin();
        this.encryptedPassword = employee.getEncryptedPassword();
        this.passwordSalt = employee.getPasswordSalt();
        this.role = employee.getRole();
    }
    
    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login;}
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }
    public void setPasswordSalt(byte[] passwordSalt) { this.passwordSalt = passwordSalt; }
    public void setRole(String role) { this.role = role;}

    public int getId() { return id;}
    public String getLogin() { return login; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public byte[] getPasswordSalt() { return passwordSalt; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "EmployeeDBDto [id=" + id + ", login=" + login + ", encryptedPassword=" + encryptedPassword
                + ", passwordSalt=" + Arrays.toString(passwordSalt) + ", role=" + role + "]";
    }
    
}
