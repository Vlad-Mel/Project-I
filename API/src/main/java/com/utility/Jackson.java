package com.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson {

    static public String objectToString(Object obj) {
        
        ObjectMapper objMapper = new ObjectMapper();

        try { 
            return objMapper.writeValueAsString(obj); 
        }
        catch (JsonProcessingException e) { e.printStackTrace(); }
    
        return null;
    }
}
