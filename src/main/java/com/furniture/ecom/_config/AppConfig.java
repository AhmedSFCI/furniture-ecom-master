/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config;

import com.furniture.ecom._helpers.GlobalConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author HP
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    //    @Bean
    //    public WebMvcConfigurer corsConfigurer() {
    //        return new WebMvcConfigurer() {
    //            @Override
    //            public void addCorsMappings(CorsRegistry registry) {
    //                registry.addMapping("/**")
    //                        .allowedMethods("GET", "POST", "PUT", "DELETE")
    //                        .allowedHeaders("*")
    //                        .allowedOrigins("http://localhost:4200/");
    //            }
    //        };
    //    }
    //    @Override
    //    public void addCorsMappings(CorsRegistry registry) {
    //        registry.addMapping("/**")
    //                .allowedMethods("GET", "POST");
    //    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(GlobalConstants.CROSS_ORIGIN);
            }
        };
    }
    //    @Override
    //    public void addCorsMappings(CorsRegistry registry) {
    //        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("GET", "POST", "DELETE", "HEAD", "PUT")
    //                .allowedHeaders("Content-Type", "Authorization", "Cache-Control")
    //                .allowedHeaders("Access-Control-Allow-Methods")
    //                .allowCredentials(true);
    //    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final ByteArrayHttpMessageConverter bodyConverter = new ByteArrayHttpMessageConverter();
        final List<MediaType> list = new ArrayList();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        list.add(MediaType.MULTIPART_FORM_DATA);
        list.add(MediaType.ALL);
        bodyConverter.setSupportedMediaTypes(list);
        converters.add(bodyConverter);
    }

}
