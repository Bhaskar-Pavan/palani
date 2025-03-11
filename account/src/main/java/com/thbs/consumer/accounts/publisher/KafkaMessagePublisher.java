package com.thbs.consumer.accounts.publisher;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagePublisher {

    private final StreamBridge streamBridge;

    public KafkaMessagePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishToKafka(String topic, Object message) {
        Message<Object> kafkaMessage = MessageBuilder.withPayload(message).setHeader("contentType", "application/json").build();
        streamBridge.send(topic, kafkaMessage);
    }
}
