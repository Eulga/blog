package com.example.bloghw2.global.jwtutil;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // response header의 'Bearer ' 문자열 제거
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new TokenValidException("Invalid JWT signature", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new TokenValidException("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new TokenValidException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new TokenValidException("JWT claims is empty", e);
        } catch (RuntimeException e){
            logger.error("An unknown error occurred, 알 수 없는 오류가 발생했습니다.");
            throw new TokenValidException("An unknown error occurred", e);
        }
    }

    // 토큰에서 사용자 정보 추출
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    public void addJwtToHeader(String token, HttpServletResponse res) {
        res.addHeader(AUTHORIZATION_HEADER, token);
    }

    // HttpServletRequest 에서 Header 토큰 가져오기
    public String getTokenFromRequestHeader(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION_HEADER);
        if(token != null) {
            return token;
        }
        return null;
    }
}
