//package com.icia.recipe.controller.mainController;
//
//import com.icia.recipe.entity.UserRoleEnum;
//import com.icia.recipe.jwt.JwtUtil;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.CookieValue;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class AuthController {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private final JwtUtil jwtUtil;
//
//    @GetMapping("/create-cookie")
//    public String createCookie(HttpServletResponse res) {
//        addCookie("Robbie Auth", res);
//
//        return "createCookie";
//    }
//
//    @GetMapping("/get-cookie")
//    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
//        System.out.println("value = " + value);
//
//        return "getCookie : " + value;
//    }
//
//    public static void addCookie(String cookieValue, HttpServletResponse res) {
//        try {
//            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
//
//            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // Name-Value
//            cookie.setPath("/");
//            cookie.setMaxAge(30 * 60);
//
//            // Response 객체에 Cookie 추가
//            res.addCookie(cookie);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @GetMapping("/create-jwt")
//    public String createJwt(HttpServletResponse res) {
//        // Jwt 생성
//        String token = jwtUtil.createToken("Robbie", UserRoleEnum.USER);
//
//        // Jwt 쿠키 저장
//        jwtUtil.addJwtToCookie(token, res);
//
//        return "createJwt : " + token;
//    }
//
//    @GetMapping("/get-jwt")
//    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
//        // JWT 토큰 substring
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증
//        if(!jwtUtil.validateToken(token)){
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        // 토큰에서 사용자 정보 가져오기
//        Claims info = jwtUtil.getUserInfoFromToken(token);
//        // 사용자 username
//        String username = info.getSubject();
//        System.out.println("username = " + username);
//        // 사용자 권한
//        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
//        System.out.println("authority = " + authority);
//
//        return "getJwt : " + username + ", " + authority;
//    }
//
//    @GetMapping("/get-jwt-info")
//    public String getJwtInfo(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
//        // JWT 토큰에서 "Bearer " 제거
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증
//        if (!jwtUtil.validateToken(token)) {
//            throw new IllegalArgumentException("Invalid Token");
//        }
//
//        // 사용자 정보 가져오기
//        Claims info = jwtUtil.getUserInfoFromToken(token);
//        String username = info.getSubject();
//        String role = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
//
//        return "Username: " + username + ", Role: " + role;
//    }
//
//}