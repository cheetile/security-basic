package com.dataus.template.securitybasic.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dataus.template.securitybasic.common.exception.ErrorType;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException authException) 
        throws IOException, ServletException {

        ErrorType error = ErrorType.UNAUTHORIZED_MEMBER;
        log.error("AuthenticationException ErrorType: {}", error);
        
        response.sendError(
            error.getHttpStatus().value(),
            error.getMessage());
    }
    
}
