/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config;

import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.JwtToken;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author HP
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtToken jwtToken;

    public JwtAuthFilter(JwtToken jwtToken) {
        super();
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("Filter Request For Auth");
            String token = request.getHeader(GlobalConstants.TOKEN_HEADER);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring("Bearer ".length());
            }
            if (token != null && !token.isBlank() && jwtToken.validateToken(token)) {
                String username = jwtToken.getUserFromToken(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);                
            }
        } catch (BadRequestException ex) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
