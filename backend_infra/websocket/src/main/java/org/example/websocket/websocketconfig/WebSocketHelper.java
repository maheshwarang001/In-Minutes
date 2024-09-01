package org.example.websocket.websocketconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
public class WebSocketHelper {

    public static String userIdKey = "userID";

    public static String getUserIdFromSessionAttribute(WebSocketSession webSocketSession){
        log.info((String) webSocketSession.getAttributes().get(userIdKey));
        return (String) webSocketSession.getAttributes().get(userIdKey);
    }

    public static String getUserIdFromUrl(ServerHttpRequest request) {
        // Get query parameters from the request
        String query = request.getURI().getQuery(); // This gets the entire query string

        if (query != null && !query.isEmpty()) {
            // Parse the query string
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length == 2 && "userId".equals(pair[0])) {
                    return pair[1];
                }
            }
        }

        throw new InvalidRequestException("userId: Missing");
    }

    public static byte[] serialize(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj).getBytes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}