package com.example.bloghw2.global;

import com.example.bloghw2.global.config.dto.ExceptionDTO;
import com.example.bloghw2.post.Exception.PostNotFoundException;
import com.example.bloghw2.user.Exception.PasswordMismatchException;
import com.example.bloghw2.user.Exception.UserDuplicationException;
import com.example.bloghw2.user.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.bloghw2.user.dto.BaseResponseDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDTO> runtimeExceptionHandler(Exception e){
        BaseResponseDTO errorResponse = new BaseResponseDTO("false",400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> validationExceptionHandler(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ExceptionDTO errorResponse = new ExceptionDTO("false",400, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ExceptionDTO> passwordMismatchExceptionHandler(PasswordMismatchException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",401, errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionDTO> postNotFoundExceptionHandler(PostNotFoundException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> userNotFoundExceptionHandler(PostNotFoundException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity<ExceptionDTO> userDuplicationExceptionHandler(UserDuplicationException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",409, errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
