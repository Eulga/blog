package com.example.bloghw2.user.service;

import com.example.bloghw2.user.dto.BaseResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;

public interface UserService {

    BaseResponseDTO signup(UserRequestDTO userRequestDTO);

    BaseResponseDTO login(UserRequestDTO userRequestDTO);

}
