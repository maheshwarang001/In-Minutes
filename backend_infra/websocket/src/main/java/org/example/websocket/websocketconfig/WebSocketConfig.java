package org.example.websocket.websocketconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.Map;

@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {


    @Autowired
    private SessionManager webSocketSessionManager;


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(new Handler(this.webSocketSessionManager),"rider/socket")
                .addInterceptors(getParametersInterceptors())
                .setAllowedOrigins("*")

               ;
    }

    @Bean
    public HandshakeInterceptor getParametersInterceptors() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String userId = WebSocketHelper.getUserIdFromUrl(request);
                attributes.put(WebSocketHelper.userIdKey, userId);
                return true;
            }

            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }
}



