package com.security.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    
    @Autowired 
    private UserDetailsService userDetailsService;

//    i made it for mapping all with the token user details
    // ThreadLocal to store the username for the current thread/request
    private static final ThreadLocal<String> currentUserName = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	

        // Get JWT token from request
        String token = getTokenFromRequest(request);

        // Validate JWT token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            // Get username from token
            String userName = jwtTokenProvider.getUserName(token);
            System.out.println("############### Jwt filter: " + userName);

            // Store the username in ThreadLocal
            currentUserName.set(userName);

            // Load the user associated with the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            System.out.println("THIS IS TESTING OF AUTHORITIES&&&&&&&&&&&&&&&&&&&&&: " + userDetails.getAuthorities());

            
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

        // Clear the ThreadLocal to avoid memory leaks
        currentUserName.remove();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    
// this method contains user name
    public String getUsername() {
        // Retrieve the username from ThreadLocal
        return currentUserName.get();
    }
}
