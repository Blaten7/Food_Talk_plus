package com.icia.recipe.controller.mainController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.recipe.entity.UserRoleEnum;
import com.icia.recipe.jwt.JwtUtil;
import com.icia.recipe.service.mainService.KakaoService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.lang.instrument.ClassDefinition;
import java.net.URLEncoder;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final KakaoService kSer;

    private final JwtUtil jwtUtil;

    @PostMapping("/extend-token")
    public ResponseEntity<?> extendToken(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                }
            }
        }
        // 토큰 확인
        if (token == null || !token.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        String jwt = token.replace("Bearer%20", ""); // Bearer 접두사 제거
        if (!jwtUtil.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

        Claims claims = jwtUtil.getUserInfoFromToken(jwt); // JWT에서 사용자 정보 추출
        String username = (String) claims.get("name");
        String role = (String) claims.get(JwtUtil.AUTHORIZATION_KEY);

        // 새로운 토큰 생성
        String newToken = jwtUtil.createToken(username, UserRoleEnum.valueOf(role));

        // 새 토큰 반환
        return ResponseEntity.ok(Map.of("newToken", newToken));
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        // 쿠키에서 Authorization 값 추출
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                }
            }
        }
        // 토큰 확인
        if (token == null || !token.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        String jwt = token.replace("Bearer%20", "");

        if (!jwtUtil.validateToken(jwt)) { // 토큰 유효성 검사
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

        Claims claims = jwtUtil.getUserInfoFromToken(jwt); // 토큰에서 정보 추출
        String role = (String) claims.get(JwtUtil.AUTHORIZATION_KEY);
        String name = (String) claims.get("name");

        return ResponseEntity.ok(Map.of("name", name, "role", role));
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse resp) throws JsonProcessingException {
        System.out.println("리다이렉션 성공");
        String token = kSer.kakaoLogin(code);
        System.out.println("카카오 로그인 사용자 정보 : " + token);
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        resp.addCookie(cookie);
        return "redirect:/";
    }





}