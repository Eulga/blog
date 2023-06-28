package com.example.bloghw2.filter;

import com.example.bloghw2.jwtutil.JwtUtil;
import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.getMethod();
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/signup") || url.startsWith("/api/login") || (url.startsWith("/api/posts") && httpServletRequest.getMethod().equals("GET")))) {
            // 회원가입, 로그인, 게시글 조회 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            log.info(httpServletRequest.getHeader(JwtUtil.AUTHORIZATION_HEADER));

//            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            String tokenValue = httpServletRequest.getHeader(JwtUtil.AUTHORIZATION_HEADER);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }
}