/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config;

import com.furniture.ecom._helpers.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 *
 * @author HP
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtToken jwtToken;

    String URLS[] = {
        "/Admin/login",
        "/Account/login",
        "/Account/forgetPassword",
        "/Account/resetPassword",
        "/Account/register",
        "/configuration/security",
        "/Product/getProductList",
        "/Product/getProductByCode",
        "/Category/getCategoryList",
        "/About/getActiveAboutList",
        "/Policy/getActivePolicyList",
        "/Material/getMaterialList",
        "/Finish/getFinishList",
        "/Style/getStyleList",
        "/Color/getColorList",
        "/v2/api-docs","/v3/api-docs","/configuration/ui","/swagger-ui/**","/swagger-resources/**","/swagger-ui.html","/webjars/**"
    };
    @Bean()
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(URLS).permitAll()
                //   .antMatchers("/Users/addUser").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtToken));
    }
    
}
