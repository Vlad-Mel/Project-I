package com.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    public String login;
    public String password;

    public EmployeeDto () {};
    
}
