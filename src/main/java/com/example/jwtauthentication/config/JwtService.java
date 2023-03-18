package com.example.jwtauthentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {


    // TODO IMPORTANT : PUT THIS KEY IN A ENV VARIABLE !!!
    // THIS IS JUST FOR DEMO 
    private static final String SECRET_KEY = "67556B58703273357638792F423F4528472B4B6250655368566D597133743677"


    public String extractUsername(String token) {
        return null;
    }

    public <T> T extracClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extracAllClaims(token);
        return claimsResolver.apply(claims);
    }




    // extract the payload of the jwt, the claims :
    private Claims extracAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
