package com.example.bloghw2.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bloghw2.global.dto.ExceptionDTO;
import com.example.bloghw2.jwtutil.TokenValidException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(1)
@Component
public class AuthExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request,response);
        } catch (TokenValidException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ExceptionDTO responseBody = new ExceptionDTO("false",HttpStatus.UNAUTHORIZED.value(),
                Collections.singletonMap("errors",e.getMessage()));
            objectMapper.writeValue(response.getWriter(),responseBody);
        } catch (IllegalArgumentException e){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ExceptionDTO responseBody = new ExceptionDTO("false",HttpStatus.BAD_REQUEST.value(),
                Collections.singletonMap("errors",e.getMessage()));
            objectMapper.writeValue(response.getWriter(),responseBody);
        }
    }
}
