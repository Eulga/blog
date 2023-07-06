package com.example.bloghw2.global.exception;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.bloghw2.domain.comment.exception.CommentNotFoundException;
import com.example.bloghw2.domain.comment.exception.CommentPermissionException;
import com.example.bloghw2.domain.user.exception.AdminTokenMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bloghw2.global.exception.dto.ExceptionDTO;
import com.example.bloghw2.domain.post.exception.PostPermissionException;
import com.example.bloghw2.domain.post.exception.PostNotFoundException;
import com.example.bloghw2.domain.user.exception.PasswordMismatchException;
import com.example.bloghw2.domain.user.exception.UserDuplicationException;
import com.example.bloghw2.domain.user.exception.UserNotFoundException;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> runtimeExceptionHandler(Exception e){
        log.error(e.getMessage());
        Map<String, String> errors = Collections.singletonMap("error","RuntimeException occurred");
        ExceptionDTO errorResponse = new ExceptionDTO("false",400, errors);
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

    // 관리자 토큰 불일치
    @ExceptionHandler(AdminTokenMismatchException.class)
    public ResponseEntity<ExceptionDTO> adminTokenMismatchExceptionHandler(AdminTokenMismatchException e){
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

    // 게시글에 권한 없음
    @ExceptionHandler(PostPermissionException.class)
    public ResponseEntity<ExceptionDTO> permissionExceptionHandler(PostPermissionException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",403, errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 댓글 정보 없음
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionDTO> postNotFoundExceptionHandler(CommentNotFoundException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 댓글에 관한 권한 없음
    @ExceptionHandler(CommentPermissionException.class)
    public ResponseEntity<ExceptionDTO> permissionExceptionHandler(CommentPermissionException e){
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",403, errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
