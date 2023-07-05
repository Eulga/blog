package com.example.bloghw2.jwtutil.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bloghw2.global.exception.dto.ExceptionDTO;
import com.example.bloghw2.jwtutil.TokenValidException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j(topic = "AuthExceptionFilter")
@RequiredArgsConstructor
@Order(1)
@Component
public class AuthExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        } catch (TokenValidException e){
            handleException(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e){
            handleException(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        log.error(message);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errors = Collections.singletonMap("error", message);
        ExceptionDTO responseBody = new ExceptionDTO("false", status.value(), errors);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
