package org.example.otp_service.Service;

import jakarta.ws.rs.Produces;
import lombok.extern.slf4j.Slf4j;
import org.example.otp_service.Entity.RedisEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class QueueConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    ProducerOtp producerOtp;

    @Autowired
    RedisEntity redis;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){

        log.info(String.format("Received message -> %s", message));

        String secretKey = producerOtp.generateSecretKey();
        String otp = producerOtp.generateOtp(secretKey,6);
        String[] divideMessage = message.split("\\s+");


        if (divideMessage.length < 2) {
            log.error("Invalid message format");
            return;
        }

        executor.execute(()->
        {
            //goes to redis ... pushed to redis and then emails

            boolean isInserted = redis.redisCallBack(divideMessage[1],otp);
            if (isInserted) {
                emailService.sendVerificationEmail(divideMessage[1], otp);
            } else {
                log.error("Failed to insert OTP into Redis");
            }
        });

    }


}
