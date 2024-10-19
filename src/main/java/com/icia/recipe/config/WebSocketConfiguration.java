package com.icia.recipe.config;

import com.icia.recipe.home.controller.EchoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final EchoHandler echoHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //클라이언트에서 웹소켓에 접속하기위한 경로(ex: localhost:8080/push)
        //도메인이 다른 서버에서도 접속 가능하도록 CORS 설정 추가
        //소켓을 지원하지 않는 브라우저의 경우 SockJS 사용하도록 설정 추가
        registry.addHandler(echoHandler, "/ws/alert").setAllowedOriginPatterns("*");
    }
}
