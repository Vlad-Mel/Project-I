package com.Interfaces;

import io.javalin.http.Context;

public interface IAccountController {
    
    public void register(Context ctx);

    public void login(Context ctx);

    public void logout(Context ctx);
}
