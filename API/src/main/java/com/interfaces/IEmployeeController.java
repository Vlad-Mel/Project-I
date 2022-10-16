package com.Interfaces;

import io.javalin.http.Context;

public interface IEmployeeController {
    
    void getAllEmployees(Context ctx);

    void getAnEmployee(Context ctx);

    void deleteAnEmployee (Context ctx);

    void updateAnEmployee (Context ctx);

}
