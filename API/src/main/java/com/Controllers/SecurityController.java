package com.Controllers;

import com.Models.Employee;
import com.Utilities.JWToken;

import io.javalin.http.Context;

public class SecurityController {
    public static void authorizedRoute(Context ctx) {
        try {
            Employee employee = JWToken.extractEmployeeFromToken(ctx.cookie("token"));

            /* If user is not authenticated. Send to unauthorized route */
            if (employee == null) ctx.redirect("/api/unauthorized");

            /* If a user is manager. Allow it anything. */
            if (employee.getRole().equals("Manager")) return;

            /* A user cannot delete or update anything that do not belong to him. */
            int employeeId = Integer.parseInt(ctx.pathParam("id"));
            if (ctx.method().toString().equals("GET") || employee.getId() == employeeId) return; 
            else ctx.redirect("/api/unauthorized");

        } catch (Exception e) {
            ctx.redirect("/api/unauthorized");
            e.printStackTrace();
        }
    }

    /**
     * The security route controller allows access to the next routes only authenticated employees with the ID
     * that matches with route and Managers.
     */
    public static void ownersRoute(Context ctx) {
        try {
            Employee employee = JWToken.extractEmployeeFromToken(ctx.cookie("token"));
            int employeeId = Integer.parseInt(ctx.pathParam("id"));

            if (employee.getId() == employeeId || employee.getRole().equals("Manager")) return;

            ctx.redirect("/api/unauthorized");
        } catch (Exception e) {
            ctx.redirect("/api/unauthorized");
            e.printStackTrace();
        }
    }

    public static void onlyManagersRoute(Context ctx) {
        try {
            Employee employee = JWToken.extractEmployeeFromToken(ctx.cookie("token"));

            if (employee.getRole().equals("Manager")) return;

            ctx.redirect("/api/unauthorized");
        } catch (Exception e) {
            ctx.redirect("/api/unauthorized");
            e.printStackTrace();
        }
    }
}
