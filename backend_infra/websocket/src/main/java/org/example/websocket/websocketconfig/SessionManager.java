package org.example.websocket.websocketconfig;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class SessionManager {


    public final Map<String , WebSocketSession> webSocketSessionMap = new HashMap<>();


    public void addWebSocketSession(WebSocketSession webSocketSession)throws Exception{

        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        webSocketSessionMap.put(userId,webSocketSession);

        log.info("websocket connection added " + webSocketSession.getId() + userId);

    }

    public void removeWebSocketSession(WebSocketSession webSocketSession){
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        webSocketSessionMap.remove(userId);
        log.info("websocket connection removed " + webSocketSession.getId() + userId);

    }

    public Optional<WebSocketSession> getWebSocketSession(String userId){
        log.info("websocket connection queried " + userId);
        return Optional.ofNullable(webSocketSessionMap.get(userId));

    }

    public List<WebSocketSession> getAllSessionFromRedis(){

        try {

            return webSocketSessionMap.values().stream()
                    .filter(Objects::nonNull)
                    .map(WebSocketSession.class::cast)
                    .filter(WebSocketSession::isOpen)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error retrieving sessions from Redis: " + e.getLocalizedMessage(), e);
            throw e;
        }
    }




}
