package com.Controllers;

import com.DTOs.EmployeeDto;
import com.Database.EmployeeDAO;
import com.ErrorHandlers.ErrorMessage;
import com.Models.Employee;
import com.Utilities.PasswordUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class AccountController {

    /* Database Access Object of Employee */
    EmployeeDAO employeeDAO = new EmployeeDAO();

    public void register(Context ctx) {

        try {
            EmployeeDto employeeDto = ctx.bodyAsClass(EmployeeDto.class);

            /* If a user already exists in database return */
            if (employeeDAO.findAnEmployee(employeeDto.login) != null) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorMessage("User already exists in Database"));
                return;
            }

            if (employeeDto.login.isEmpty() || employeeDto.password.isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorMessage("Login and/or password field is/are empty."));
                return;
            }
            
            /* Employee add to database */ 
            employeeDAO.addEmployee(new Employee(employeeDto));

            /* Response */ 
            ctx.status(HttpStatus.CREATED);
            ctx.json(employeeDto);
        } catch (Exception e) {
            System.err.println(e);
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Unresolved error."));
        }
    }


    public void login(Context ctx) {
        try {
            EmployeeDto employeeDto = ctx.bodyAsClass(EmployeeDto.class);
            
            /* Check if such user is in DB */ 
            Employee employee = employeeDAO.findAnEmployee(employeeDto.login);

            /* If no employee found, return */
            if (employee == null) {
                ctx.status(HttpStatus.UNAUTHORIZED);
                ctx.json(new ErrorMessage("The user does not exist"));
                return;
            }

            /* Extract actual and provided encrypted passwords */
            String actualPwd = employee.getEncryptedPassword();
            String providedPwd = PasswordUtility.generateEncryptedPassword(employeeDto.password, employee.getPasswordSalt());

            /* Compares two encrypted passwords */
            if (actualPwd.equals(providedPwd)) {
                ctx.status(HttpStatus.OK);
                ctx.json(employee);
                return;
            } 

            /* Response */
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.json(new ErrorMessage("The user's password is wrong. Try again."));
        } catch (Exception e) {
            System.err.println(e);
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Unresolved error."));
        }
    }

    public void logout(Context ctx) {
        try {
            /* Trying to remove JWT token from Cookie storage */
            ctx.removeCookie("token");
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new ErrorMessage("Cannot logout."));
        }
        
    }
    
}
