package com.thbs.consumer.accounts.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQSubscriber {

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        log.info("Received message from RabbitMQ: {}", message);
    }
}
