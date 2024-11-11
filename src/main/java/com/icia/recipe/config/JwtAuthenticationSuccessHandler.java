package com.icia.recipe.config;

import com.icia.recipe.entity.UserRoleEnum;
import com.icia.recipe.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용자 정보 가져오기
        String username = authentication.getName(); // 로그인된 사용자 이름
        UserRoleEnum role = (UserRoleEnum) authentication.getAuthorities().stream()
                .map(grantedAuthority -> UserRoleEnum.valueOf(grantedAuthority.getAuthority())) // 이름이 일치하므로 정상 동작
                .findFirst()
                .orElse(UserRoleEnum.ROLE_USER); // 기본 권한 설정

        // JWT 생성
        String token = jwtUtil.createToken(username, role);

        // JWT를 쿠키에 저장
        jwtUtil.addJwtToCookie(token, response);

        // 리다이렉트 설정 (로그인 성공 후 이동할 URL)
        response.sendRedirect("/");
    }
}
