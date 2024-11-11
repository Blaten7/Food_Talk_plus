package com.icia.recipe.jwt;

import com.icia.recipe.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // JWT 데이터
        // Header KEY 값
        public static final String AUTHORIZATION_HEADER = "Authorization";

        // 사용자 권한 값의 KEY
        public static final String AUTHORIZATION_KEY = "auth";

        // Token 식별자
        public static final String BEARER_PREFIX = "Bearer ";

        // 토큰 만료시간
        private final long TOKEN_EX_TIME = 15 * 60 * 1000L; // 15분

        @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
        private String secretKey;
        private Key key;
        private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 로그 설정
        public static final Logger logger = LoggerFactory.getLogger("== JWT ==");

        @PostConstruct
        public void init() {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
        }

    // JWT 생성
        // 토큰 생성
        public String createToken(String username, UserRoleEnum role) {
            Date date = new Date();

            return BEARER_PREFIX +
                    Jwts.builder()
                            .setSubject(username) // 사용자 식별자값(ID)
                            .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                            .setExpiration(new Date(date.getTime() + TOKEN_EX_TIME)) // 만료 시간
                            .setIssuedAt(date) // 발급일
                            .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                            .compact();
        }

    // 생성된 JWT를 COOKIE에 저장
        public void addJwtToCookie(String token, HttpServletResponse res) {
            try {
                token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

                Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
                cookie.setPath("/");

                // Response 객체에 Cookie 추가
                res.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }

    // COOKIE에 들어있던 JWT 토큰을 SUBSTRING
        public String substringToken(String tokenValue) {
            if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
                return tokenValue.substring(7);
            }
            logger.error("Not Found Token");
            throw new NullPointerException("Not Found Token");
        }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    // JWT에서 사용자 정보 가져오기
        public Claims getUserInfoFromToken(String token) {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }
}



