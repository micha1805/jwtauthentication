package com.example.jwtauthentication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// The abstract class OncePerRequestFilter reads every request, it itself inherits from other classes
// To have a bean, more specifically a component
@Component
// To build automatically a constructors with all the private fields declared
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    // Interface to get Users :
    private final UserDetailsService userDetailsService;

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
        // Get the content of the Authorization header in the request :
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // because the full header is like so : "Authorization: Bearer BLABLABLABLA"
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            // The equivalent of "next"  in ExpressJS middleware :
            filterChain.doFilter(request, response);
            return; // We just quit the current method
        }
        // beginIndex is 7 because "Bearer " is 7 characters long
        jwt = authHeader.substring(7);
        // Extract from the JWT, by convention it is Username,
        // even if in our case we look at the email
        userEmail = jwtService.extractUsername(jwt);
        // the second part checks if the user is not yet connected
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Like next() in ExpresJS :
        filterChain.doFilter(request, response);
    }
}
