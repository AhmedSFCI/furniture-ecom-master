/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config;

import com.furniture.ecom._helpers.JwtToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author HP
 */
public class JwtSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    final private JwtToken jwtToken;

    public JwtSecurityConfigurer(JwtToken jwtToken) {
        super();
        this.jwtToken = jwtToken;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtAuthFilter filter = new JwtAuthFilter(jwtToken);
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
