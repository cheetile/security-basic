package com.dataus.template.securitybasic.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum ErrorType {

    // 400 BAD_REQUEST
    REGISTERED_ID(BAD_REQUEST, "This id already registered"),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "Failed to authorize"),
    MEMBER_NO_AUTHORITY(UNAUTHORIZED, "Unauthorized access"),

    // 404 NOT_FOUND
    UNAVAILABLE_PAGE(NOT_FOUND, "This page not found"),
    NO_MEMBER_NUM(NOT_FOUND, "This member number doesn't exist"),

    // 409 CONFLICT
    ;

    private HttpStatus httpStatus;
    private String message;

    public RuntimeException getException() {
        log.error("CustomException ErrorType: {}", this);
        return new ResponseStatusException(this.httpStatus, this.message);
    }

    @Override
    public String toString() {
        return String.format(
            "[%s(%s, %s)]", 
            this.name(), 
            this.httpStatus, 
            this.message);
    }
    
}
