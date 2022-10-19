package com.Services;

import com.DTOs.EmployeeDBDto;
import com.Utilities.JWToken;

import io.javalin.http.Context;
import io.javalin.json.JavalinJackson;

public class CookieService {

    /** 
     * A JWT cookie will be granted to a user ONLY if the user successfully logged in.
     */
    static public void grantCookie(Context ctx) {
        String error = "\"error\":\"error\"";

        /* If the response does not contain error, grant a cookie */
        if (!ctx.result().contains(error)) {
            EmployeeDBDto employeeDBDto = new JavalinJackson().fromJsonString(ctx.result(), EmployeeDBDto.class);
            ctx.cookie("token", JWToken.createToken(employeeDBDto));
        }
    }
}
