package com.example.blog.domain.user.controller;

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

    // 로그인은 global - jwt - JwtAuthenticationFilter 에서 처리
    /*
        public JwtAuthenticationFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
            setFilterProcessesUrl("/api/user/login");
        }
     */
}