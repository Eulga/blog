package com.example.bloghw2.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloghw2.user.dto.BaseResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;
import com.example.bloghw2.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO> login(@Valid @RequestBody UserRequestDTO userRequestDTO){

        return null;
    }
}
