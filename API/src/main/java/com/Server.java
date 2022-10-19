package com;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.Controllers.AccountController;
import com.Controllers.EmployeeController;
import com.Controllers.ManagerController;
import com.Controllers.SecurityController;
import com.Controllers.TicketController;
import com.Database.InitializationDAO;
import com.ErrorHandlers.ErrorMessage;
import com.Services.CookieService;


public class Server {

    static private Javalin app = Javalin.create().start(7070); 
    
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
        ManagerController managerController = new ManagerController();

        app.routes(() -> {
            path("api", () -> {

                path("employees", () -> {

                    get(employeeController::getAllEmployees);

                    path("{id}", () -> {
                        before(SecurityController::authorizedRoute);
    
                        get(employeeController::getAnEmployee);
                        // post(employeeController::updateAnEmployee);
                        // delete(employeeController::deleteAnEmployee);


                        path("tickets", () -> {
                            before(SecurityController::ownersRoute);

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
                    
                    after(CookieService::grantCookie);
                });

                path("logout", () -> {
                    get(accountController::logout);
                });

                path("tickets", () -> {
                    before(SecurityController::onlyManagersRoute);

                    get(ticketController::getAllPendingTickets);
                    post(managerController::updateTicketStatus);

                });

                path("unauthorized", () -> {
                    get(ctx -> ctx.json(new ErrorMessage("Not authorized")));
                });
            });
        });
        
    }

    /* Initialization of the tables and populating them with some records. */
    private static void initialization() {
        InitializationDAO initializationDAO = new InitializationDAO();

        initializationDAO.initializeTables();
        initializationDAO.populateTables();
    }
}
