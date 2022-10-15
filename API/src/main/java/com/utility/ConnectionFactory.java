package com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    /**
    * Establish connection with database. Return connection that MUST BE CLOSED after usage. 
    * @return Connection
    */
    protected Connection establishConnection() {
        try {
            return DriverManager.getConnection(
                ApplicationProperties.getProperty("url"), 
                ApplicationProperties.getProperty("username"), 
                ApplicationProperties.getProperty("password")
            );
        } 
        catch (SQLException e) { e.printStackTrace(); } 
        
        return null;
    }
}
