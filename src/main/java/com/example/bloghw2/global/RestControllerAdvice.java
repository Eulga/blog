package com.example.bloghw2.global;

import com.example.bloghw2.global.dto.ExceptionDTO;
import com.example.bloghw2.post.Exception.PermissionException;
import com.example.bloghw2.post.Exception.PostNotFoundException;
import com.example.bloghw2.user.Exception.PasswordMismatchException;
import com.example.bloghw2.user.Exception.UserDuplicationException;
import com.example.bloghw2.user.Exception.UserNotFoundException;
import com.example.bloghw2.user.dto.BaseResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDTO> runtimeExceptionHandler(Exception e){
        BaseResponseDTO errorResponse = new BaseResponseDTO("false",400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // requestDTO 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> validationExceptionHandler(MethodArgumentNotValidException e){
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ExceptionDTO errorResponse = new ExceptionDTO("false",400, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 비밀번호 불일치
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ExceptionDTO> passwordMismatchExceptionHandler(PasswordMismatchException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",401, errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // 유저 정보 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> userNotFoundExceptionHandler(UserNotFoundException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 사용자 이름 중복
    @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity<ExceptionDTO> userDuplicationExceptionHandler(UserDuplicationException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",409, errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // 게시글 정보 없음
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionDTO> postNotFoundExceptionHandler(PostNotFoundException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 권한 없는 사용자의 게시글 수정, 삭제
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ExceptionDTO> permissionExceptionHandler(PermissionException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",403, errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
