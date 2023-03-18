package com.example.jwtauthentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {


    // TODO IMPORTANT : PUT THIS KEY IN A ENV VARIABLE !!!
    // THIS IS JUST FOR DEMO 
    private static final String SECRET_KEY = "67556B58703273357638792F423F4528472B4B6250655368566D597133743677"


    public String extractUsername(String token) {
        // Subject is email or username of the user,
        return extracClaim(token, Claims::getSubject);
    }

    public <T> T extracClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extracAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Valid for a day :
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extracClaim(token, Claims::getExpiration)
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
