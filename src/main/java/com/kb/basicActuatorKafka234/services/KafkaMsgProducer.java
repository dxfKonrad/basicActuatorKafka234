package com.kb.basicActuatorKafka234.services;

import com.kb.basicActuatorKafka234.model.KafkaTestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMsgProducer {

    private static final String PROPERTIES_BINDING_NAME = "test-kafka-topic-out-0";

    private final StreamBridge streamBridge;

    public void sendMessage(KafkaTestEvent event) {
        if (!streamBridge.send(PROPERTIES_BINDING_NAME, event)) {
            log.warn("could not send a message!");
        }
    }


}
