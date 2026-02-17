package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.events.LogEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogProducer {
    private final KafkaTemplate<String, LogEvent> kafkaTemplate;
    public void sendLog(LogEvent event) {

        kafkaTemplate.send(
                "log-events",
                event.getServiceName(),
                event
        );

        System.out.println("Log sent: " + event);
    }
}
