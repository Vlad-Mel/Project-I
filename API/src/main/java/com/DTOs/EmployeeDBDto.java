package com.DTOs;

public class EmployeeDBDto {
    public String login;
    public String encryptedPassword;
    public byte[] passwordSalt;
    public String role;
    
    public EmployeeDBDto(String login, String encryptedPassword, byte[] passwordSalt, String role) {
        this.login = login;
        this.encryptedPassword = encryptedPassword;
        this.passwordSalt = passwordSalt;
        this.role = role;
    } 
}
