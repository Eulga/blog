package com.example.bloghw2.user.service;

import com.example.bloghw2.user.dto.UserResponseDTO;
import com.example.bloghw2.user.dto.LoginResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;

public interface UserService {

    UserResponseDTO signup(UserRequestDTO userRequestDTO);

    LoginResponseDTO login(UserRequestDTO userRequestDTO);

}
