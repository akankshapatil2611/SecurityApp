package com.security.SecurityApp.service;

import com.security.SecurityApp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //
    public String generetedAccessToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())    // identify whose this token belongs to
                .claim("email", user.getEmail())  // information stored inside JWT payload
                .issuedAt(new Date())   // token generation time
                .expiration(new Date(System.currentTimeMillis()+1000*60*10)) // expiration time
                .signWith(getSecretKey())  // JWt uses secret key to generate signature
                .compact();  // converts to string
    }


    public String generetedRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())    // identify whose this token belongs to
                .issuedAt(new Date())   // token generation time
                .expiration(new Date(System.currentTimeMillis()+1000L *60*60*24*30*6)) // expiration time
                .signWith(getSecretKey())  // JWt uses secret key to generate signature
                .compact();  // converts to string
    }


    // extract userId from token
    public Integer getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Integer.valueOf(claims.getSubject());
    }
}
