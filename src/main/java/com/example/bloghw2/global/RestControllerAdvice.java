package com.example.bloghw2.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.bloghw2.user.dto.BaseResponseDTO;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDTO> RuntimeExceptionHandler(Exception e){
        BaseResponseDTO errorResponse = new BaseResponseDTO("false",400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDTO> validationExceptionHandler(Exception e){
        BaseResponseDTO errorResponse = new BaseResponseDTO("false",400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
