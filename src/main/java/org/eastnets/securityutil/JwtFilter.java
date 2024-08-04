package org.eastnets.securityutil;

import org.eastnets.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter for JWT (JSON Web Token) authentication.
 * <p>
 * This filter is responsible for extracting JWT tokens from the HTTP request,
 * validating them, and setting the appropriate authentication context for the user
 * if the token is valid.
 * </p>
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService service;

    /**
     * Processes the HTTP request to extract and validate JWT tokens.
     * <p>
     * If a valid token is found, this method sets the authentication context
     * for the user based on the token's information.
     * </p>
     *
     * @param httpServletRequest the HTTP request to be processed
     * @param httpServletResponse the HTTP response
     * @param filterChain the filter chain to pass the request and response
     * @throws ServletException if an error occurs during the request processing
     * @throws IOException if an I/O error occurs during the request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Extract the token
            userName = jwtUtil.extractUsername(token); // Extract the username from the token
        }

        // If a username is present and no authentication is set in the SecurityContext
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName); // Load user details

            // Validate the token and set authentication context if valid
            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // Continue the filter chain
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
