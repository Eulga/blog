package com.example.bloghw2.domain.user.service;

import com.example.bloghw2.global.jwt.JwtUtil;
import com.example.bloghw2.domain.user.dto.SignupRequestDTO;
import com.example.bloghw2.domain.user.dto.SignupResponseDTO;
import com.example.bloghw2.domain.user.entity.User;
import com.example.bloghw2.domain.user.entity.UserRoleEnum;
import com.example.bloghw2.domain.user.exception.AdminTokenMismatchException;
import com.example.bloghw2.domain.user.repository.UserRepository;
import com.example.bloghw2.domain.user.exception.UserDuplicationException;
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

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<SignupResponseDTO> signup(SignupRequestDTO requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // username 중복 확인
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()){
            throw new UserDuplicationException("아이디 중복");
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
