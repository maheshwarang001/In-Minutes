package org.example.websocket.websocketconfig;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.Producer.KafkaMessageProducer;
import org.example.websocket.configuration.Topics;
import org.example.websocket.dto.LocationPayLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Slf4j
public class Handler extends AbstractWebSocketHandler {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;



    public Handler(SessionManager webSocketSessionManager){
        this.sessionManager = webSocketSessionManager;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.addWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionManager.removeWebSocketSession(session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, @NonNull BinaryMessage message) throws Exception {

        ByteBuffer payloadBuffer = message.getPayload();
        byte[] payload = payloadBuffer.array();

        if (payload.length < 16) {
            throw new IllegalArgumentException("Payload is not a valid byte array");
        }

        double latitude = ByteBuffer.wrap(payload, 0, 8).getDouble();
        double longitude = ByteBuffer.wrap(payload, 8, 8).getDouble();

        log.info(latitude + " "  + longitude);

        try {

            LocationPayLoad locationPayLoad =
                    new LocationPayLoad
                            (
                                    WebSocketHelper.getUserIdFromSessionAttribute(session),
                                    payload,
                                    LocalDateTime.now()
                            );

            kafkaMessageProducer.sendMessage(locationPayLoad, Topics.LOCATION_TOPIC);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

    }




}
