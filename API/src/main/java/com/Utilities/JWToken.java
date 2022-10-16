package com.Utilities;

import com.DTOs.EmployeeDBDto;
import com.Models.Employee;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

public class JWToken {
    
    static private SecretKey key = Keys.hmacShaKeyFor(ApplicationProperties.getProperty("secretkey").getBytes()); 

    
    static public String createToken(EmployeeDBDto employee) {
        Instant now = Instant.now();

        String jws = Jwts.builder()
                         .setIssuer("server")
                         .setSubject("token")
                         .setAudience("users")
                         .claim("employee", employee)
                         .setIssuedAt(Date.from(now))
                         .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                         .signWith(key)
                         .compact();
        return jws;
    }


    static public Employee extractEmployeeFromToken(String jws) {

        try {
            EmployeeDBDto result = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .deserializeJsonWith(new JacksonDeserializer(Maps.of("employee", EmployeeDBDto.class).build()))
                            .build()
                            .parseClaimsJws(jws)
                            .getBody()
                            .get("employee", EmployeeDBDto.class);

            return new Employee(result);
        } 
        catch (SignatureException e) { 
            e.printStackTrace();
            return null; 
        } 
        catch (ExpiredJwtException e) { 
            e.printStackTrace(); 
            return null;
        }
        
    }

}
