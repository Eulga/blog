package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.LoginRequestDTO;
import com.example.blog.domain.user.dto.SignupRequestDTO;
import com.example.blog.domain.user.dto.SignupResponseDTO;
import com.example.blog.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 컨트롤러 도달하기 전에 JwtAuthenticationFilter가 가로챔 <- 로그인시 JWT 인증은 시큐리티가 하도록 함
        return ResponseEntity.ok().build();
    }
}
