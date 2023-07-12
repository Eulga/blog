package com.example.blog.global.security;

import com.example.blog.domain.user.dto.LoginRequestDTO;
import com.example.blog.domain.user.entity.UserRoleEnum;
import com.example.blog.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDTO requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain
            , Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status",HttpServletResponse.SC_OK);
        data.put("message", "로그인 성공");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");

        //로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면
        // "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", HttpServletResponse.SC_BAD_REQUEST);
        data.put("message", "회원을 찾을 수 없습니다.");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}
