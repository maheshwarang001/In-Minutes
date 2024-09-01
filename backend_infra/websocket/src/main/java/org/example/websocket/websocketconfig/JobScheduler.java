package org.example.websocket.websocketconfig;

import lombok.extern.slf4j.Slf4j;
import org.example.websocket.dto.OrderBroadCastDto;
import org.example.websocket.dto.OrderRiderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

@Component
@Slf4j
public class JobScheduler {

    @Autowired
    private SessionManager sessionManager;

    @Scheduled(fixedRate = 10000)
    void sendPeriodicMessages() throws IOException {
        for (WebSocketSession session : sessionManager.getAllSessionFromRedis()) {
                String broadcast = "server periodic message " + LocalTime.now() + session.getId();
                log.info("Server sends: {}", broadcast);
                session.sendMessage(new TextMessage(broadcast));
        }
    }


    public void sendOrderTicketToRider(OrderRiderDto orderRiderDto) throws IOException {

        Optional<WebSocketSession> webSocketSession = sessionManager.getWebSocketSession(orderRiderDto.getRiderId().toString());

        if(webSocketSession.isPresent()){

            OrderBroadCastDto orderBroadCastDto = OrderBroadCastDto
                    .builder()
                    .orderId(orderRiderDto.getOrderId())
                    .lat(orderRiderDto.getStoreLocation().getY())
                    .log(orderRiderDto.getStoreLocation().getX())
                    .bonus(orderRiderDto.getBonus())
                    .avgTotalDistance(orderRiderDto.getAvgTotalDistance())
                    .customerTip(orderRiderDto.getCustomerTip())
                    .deliveryCompensation(orderRiderDto.getDeliveryCompensation())
                    .totalPay(orderRiderDto.getTotalPay())
                    .handlingCost(orderRiderDto.getHandlingCost())
                    .build();

            webSocketSession.get().sendMessage(new TextMessage(orderBroadCastDto.toString()));
        }else{
            log.error("Rider Not Present");
        }

    }

}
