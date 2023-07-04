package com.example.bloghw2.user.service;

import com.example.bloghw2.user.dto.*;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse res);

}
