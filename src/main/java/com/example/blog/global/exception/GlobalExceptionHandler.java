package com.example.blog.global.exception;

import com.example.blog.domain.comment.exception.CommentNotFoundException;
import com.example.blog.domain.comment.exception.CommentPermissionException;
import com.example.blog.domain.post.exception.PostNotFoundException;
import com.example.blog.domain.post.exception.PostPermissionException;
import com.example.blog.domain.user.exception.AdminTokenMismatchException;
import com.example.blog.domain.user.exception.PasswordMismatchException;
import com.example.blog.domain.user.exception.UserDuplicationException;
import com.example.blog.domain.user.exception.UserNotFoundException;
import com.example.blog.global.exception.dto.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
// Advice <- AOP 구현
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestApiException> nullPointerExceptionHandler(NullPointerException ex) {
        RestApiException restApiException = new RestApiException("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        // 여기서는 토큰이 필요한 API 요청에서 토큰을 전달하지 않은 경우만 처리
        // 토큰은 있으나 유효하지 않은 토큰인 경우는 JwtAuthorizationFilter 에서 처리
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        //토큰이 있고, 유효한 토큰이지만 사용자가 작성한 게시글/댓글이 아닌 경우에는
        //“작성자만 삭제 또는 수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
        RestApiException restApiException = new RestApiException("작성자만 삭제 또는 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST //400
        );
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
        //DB에 이미 존재하는 username으로 회원가입을 요청한 경우
        // "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
        Map<String, String> errors = Collections.singletonMap("error", e.getMessage());
        ExceptionDTO errorResponse = new ExceptionDTO("false",400, errors);
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
