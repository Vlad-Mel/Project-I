package com.Models;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Manager extends Employee {

    final public String role;

    public Manager(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(username, password);
        this.role = "Manager";
    }


}
