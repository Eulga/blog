package com.example.bloghw2.domain.user.service;

import com.example.bloghw2.domain.user.dto.LoginRequestDTO;
import com.example.bloghw2.domain.user.dto.LoginResponseDTO;
import com.example.bloghw2.domain.user.dto.SignupRequestDTO;
import com.example.bloghw2.domain.user.dto.SignupResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse res);

}
