package com.example.bloghw2.user.service;

import com.example.bloghw2.user.dto.LoginRequestDTO;
import com.example.bloghw2.user.dto.LoginResponseDTO;
import com.example.bloghw2.user.dto.SignupRequestDTO;
import com.example.bloghw2.user.dto.SignupResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse res);

}
