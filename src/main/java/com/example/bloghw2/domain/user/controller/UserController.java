package com.example.bloghw2.domain.user.controller;

import com.example.bloghw2.domain.user.dto.LoginRequestDTO;
import com.example.bloghw2.domain.user.dto.LoginResponseDTO;
import com.example.bloghw2.domain.user.dto.SignupRequestDTO;
import com.example.bloghw2.domain.user.dto.SignupResponseDTO;
import com.example.bloghw2.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO){
        SignupResponseDTO response = userService.signup(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse res){
        LoginResponseDTO response = userService.login(loginRequestDTO, res);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
