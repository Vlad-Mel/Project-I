package com.Services;

import com.Models.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.ApplicationProperties;
import com.utility.Jackson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

public class TokenService {
    
    private SecretKey key = Keys.hmacShaKeyFor(ApplicationProperties.getProperty("secretkey").getBytes()); 


    public String createToken(String jsonEmployee) {
        Instant now = Instant.now();

        String jws = Jwts.builder()
                         .setIssuer("server")
                         .setSubject("token")
                         .setAudience("users")
                         .claim("employee", jsonEmployee)
                         .setIssuedAt(Date.from(now))
                         .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                         .signWith(key)
                         .compact();
        return jws;
    }

    public void validateToken(String jws) {
        Jws<Claims> result = Jwts.parserBuilder()
                                 .setSigningKey(key)
                                 .build()
                                 .parseClaimsJws(jws);
        
        System.out.println(result);
    }

}
