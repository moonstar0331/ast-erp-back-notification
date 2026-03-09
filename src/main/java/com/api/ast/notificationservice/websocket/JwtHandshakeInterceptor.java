package com.api.ast.notificationservice.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            // 쿼리 파라미터나 헤더에서 토큰 추출
            String token = servletRequest.getParameter("token");
            if (token == null) {
                token = servletRequest.getHeader("Authorization");
            }

            // JWT 검증 로직은 SecurityContext나 JwtDecoder를 통해 가능하지만, 
            // 여기서는 attributes에 넘겨주어 WebSocketSession에서 사용하게 함.
            if (token != null) {
                // 실제 프로젝트에서는 JWT를 파싱하여 userId를 추출해야 합니다.
                // attributes.put("token", token);
                // attributes.put("userId", userId);
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
    }
}
