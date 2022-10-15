package com;

import io.javalin.Javalin;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.Controllers.AccountController;
import com.Controllers.EmployeeController;
import com.Controllers.TicketController;
import com.Models.Employee;
import com.Services.TokenService;

public class Server {

    static Javalin app = Javalin.create().start(7070); 
    
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run() {
                app.close();
            }
        });
    }

    public static void main(String[] args) {
        
        EmployeeController employeeController = new EmployeeController();
        AccountController accountController = new AccountController();
        TicketController ticketController = new TicketController();

        app.routes(() -> {
            path("api", () -> {

                path("employees", () -> {
                    before(ctx -> {  
                        System.out.println(ctx.cookie("token"));
                    });


                    get(employeeController::getAllEmployees);


                    path("{id}", () -> {
                        get(employeeController::getAnEmployee);
                        post(employeeController::updateAnEmployee);
                        delete(employeeController::deleteAnEmployee);


                        path("tickets", () -> {
                            get(ticketController::getEmployeeTickets);
                            post(ticketController::createTicket);
                        });
                        
                    });
                });

                path("register", () -> {
                    post(accountController::register);
                });

                path("login", () -> {
                    post(accountController::login);
                    after(ctx -> {
                        System.out.println(ctx.result());
                        // ctx.cookie("token",new TokenService().createToken(ctx.result()));
                        // ctx.res();
                    });
                });

                path("logout", () -> {
                    get(accountController::logout);
                });

                path("tickets", () -> {
                    get(ticketController::getAllTickets);
                });
            });
        });
        
    }
}
