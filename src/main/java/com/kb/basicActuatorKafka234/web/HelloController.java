package com.kb.basicActuatorKafka234.web;

import com.kb.basicActuatorKafka234.model.KafkaTestEvent;
import com.kb.basicActuatorKafka234.services.KafkaMsgProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class HelloController {
    private final ApplicationEventPublisher eventPublisher;
    private final ApplicationAvailability availability;
    private final KafkaMsgProducer kafkaMsgProducer;

    @GetMapping
    public String sayHello() {
        return "hello234";
    }

    @PostMapping("/invalidate")
    public void invalidate() {
        AvailabilityChangeEvent.publish(this.eventPublisher, new Object(), LivenessState.BROKEN);
    }

    @PostMapping("/revalidate")
    public void revalidate() {
        AvailabilityChangeEvent.publish(this.eventPublisher, new Object(), LivenessState.CORRECT);
    }

    @PostMapping("/unready")
    public void makeNotReady() {
        AvailabilityChangeEvent.publish(this.eventPublisher, new Object(), ReadinessState.REFUSING_TRAFFIC);
    }

    @PostMapping("/reready")
    public void makeReadyAgain() {
        AvailabilityChangeEvent.publish(this.eventPublisher, new Object(), ReadinessState.ACCEPTING_TRAFFIC);
    }

    @PostMapping("/livenessStatus")
    public String livenessStatus() {
        return availability.getLivenessState().toString();
    }

    @PostMapping("/readinessStatus")
    public String readinessStatus() {
        return availability.getLivenessState().toString();
    }

    @PostMapping("/sendMessage")
    public void sendMessage() {
        kafkaMsgProducer.sendMessage(
                KafkaTestEvent.builder()
                        .value1(LocalDateTime.now().toString())
                        .value2("check").build());
    }
}
