package com.DTOs;

public class RegisterDto {
    public String login;
    public String password;

    public RegisterDto(){};

    public RegisterDto(String login, String password) {
        this.login = login;
        this.password = password;
    }
} 
