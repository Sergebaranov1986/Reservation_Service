package com.baranov.reservation.web;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExeptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExeptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericExeption(Exception e){
    log.error("handle exeception" , e);
    var errorDto = new ErrorResponseDto(
            "Internal server error",
            e.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorDto);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityExeption(EntityNotFoundException e){
        log.error("handle EntityNotFoundExeception" , e);
        var errorDto = new ErrorResponseDto(
                "Entity Not Found",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }
    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDto> handleIncorrectRequest(Exception e){
        log.error("handle IllegalArgumentRoStateException" , e);
        var errorDto = new ErrorResponseDto(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }
}
