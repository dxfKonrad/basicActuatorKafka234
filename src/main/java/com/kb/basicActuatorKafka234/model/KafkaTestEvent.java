package com.kb.basicActuatorKafka234.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KafkaTestEvent {
    String value1;
    String value2;
}
