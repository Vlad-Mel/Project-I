package com.Controllers;

import com.DTOs.EmployeeDto;
import com.Database.EmployeeDAO;
import com.ErrorHandlers.MessageResponse;
import com.Interfaces.IEmployeeController;
import com.Models.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.*;


public class EmployeeController implements IEmployeeController{
    
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public void getAllEmployees(Context ctx) {
        try {
            ctx.json(employeeDAO.allEmployees());
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            ctx.status(HttpStatus.UNKNOWN);
            ctx.json(new MessageResponse("Unresolved error."));
        }
        
    }

    @Override
    public void getAnEmployee(Context ctx) {
        try {
            int employeeId = Integer.parseInt(ctx.pathParam("id"));

            ctx.json(employeeDAO.findAnEmployee(employeeId));
            ctx.status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new MessageResponse("Invalid ID number"));
        }
    }

    @Override
    public void updateAnEmployee (Context ctx) {
        int employeeId = Integer.parseInt(ctx.pathParam("id"));
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeeDto employeeDto = objectMapper.readValue(ctx.body(), EmployeeDto.class);
            
            /* Replace existed employee with new one in DB */

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        ctx.json(ctx.body());
    }
}
