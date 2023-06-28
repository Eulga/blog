package com.example.bloghw2.user.service;

import com.example.bloghw2.jwtutil.JwtUtil;
import com.example.bloghw2.user.Exception.PasswordMismatchException;
import com.example.bloghw2.user.Exception.UserDuplicationException;
import com.example.bloghw2.user.Exception.UserNotFoundException;
import com.example.bloghw2.user.dto.BaseResponseDTO;
import com.example.bloghw2.user.dto.LoginResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;
import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public BaseResponseDTO signup(UserRequestDTO userRequestDTO) {
        Optional<User> findUser = userRepository.findByUsername(userRequestDTO.getUsername());

        if (findUser.isPresent()){
            throw new UserDuplicationException("아이디 중복");
        }
        User user = User.builder()
            .username(userRequestDTO.getUsername())
            .password(passwordEncoder.encode(userRequestDTO.getPassword()))
            .build();
        userRepository.save(user);

        return new BaseResponseDTO("true",201);
    }

    @Transactional
    @Override
    public LoginResponseDTO login(UserRequestDTO userRequestDTO) {
        String username = userRequestDTO.getUsername();
        String rawPassword = userRequestDTO.getPassword();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 아닙니다."));

        if (!passwordEncoder.matches(rawPassword,user.getPassword())){
            throw new PasswordMismatchException("비밀번호 오류");
        }
        String accessToken = jwtUtil.createToken(username);
        return new LoginResponseDTO("true",200,accessToken);
    }
}
