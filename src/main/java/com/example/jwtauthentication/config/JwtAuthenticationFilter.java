package com.example.jwtauthentication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// The abstract class OncePerRequestFilter reads every request, it itself inherits from other classes
// To have a bean, more specifically a component
@Component
// To build automatically a constructors with all the private fields declared
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    

    @Override
    protected void doFilterInternal(
            // the request :
            @NonNull HttpServletRequest request,
            // the response we can modify before it's  sent :
            @NonNull HttpServletResponse response,
            // Chain of responsibility design pattern :
            // Note that filters are middlewares, it's their name in the Java world
            @NonNull FilterChain filterChain


            // Rem : The @NonNull annotation is there to prevent injecting
            // a null pointer in here. If so an IllegalArgumentException
            // will ve thrown instead of a NullPointerException further in the code

    ) throws ServletException, IOException {
        // do stuff here
    }
}
