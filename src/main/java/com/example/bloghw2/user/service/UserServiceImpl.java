package com.example.bloghw2.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bloghw2.user.dto.BaseResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;
import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDTO signup(UserRequestDTO userRequestDTO) {
        Optional<User> findUser = userRepository.findByUsername(userRequestDTO.getUsername());

        if (findUser.isPresent()){
            throw new IllegalArgumentException("아이디 중복");
        }
        User user = User.builder()
            .username(userRequestDTO.getUsername())
            .userPassword(passwordEncoder.encode(userRequestDTO.getPassword()))
            .build();
        userRepository.save(user);

        return new BaseResponseDTO("true",201);
    }

    @Override
    public BaseResponseDTO login(UserRequestDTO userRequestDTO) {
        return null;
    }
}
