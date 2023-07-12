package com.example.blog.domain.user.service;

import com.example.blog.domain.user.dto.SignupRequestDTO;
import com.example.blog.domain.user.dto.SignupResponseDTO;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.entity.UserRoleEnum;
import com.example.blog.domain.user.exception.AdminTokenMismatchException;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.domain.user.exception.UserDuplicationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //private final MessageSource messageSource;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<SignupResponseDTO> signup(SignupRequestDTO requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // username 중복 확인
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()){
            throw new UserDuplicationException("중복된 username 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new AdminTokenMismatchException("잘못된 관리자 암호");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = User.builder()
            .username(username)
            .password(password)
            .role(role)
            .build();
        userRepository.save(user);

        return ResponseEntity.status(201).body(new SignupResponseDTO(201, "회원가입 완료."));
    }

}
