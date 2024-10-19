package com.icia.recipe.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		//if(exception instanceof DisabledException)
        // 인증 실패 시 수행할 동작을 정의
        // 예: 에러 메시지 표시, 로그인 페이지 리다이렉션 등
    	log.info("로그인실패 핸들러:{}",exception.getMessage());
    	request.getSession().setAttribute("msg", "로그인 실패-handler");
    	response.sendRedirect("member/login");
	}
}
