package org.example.service;


import org.example.models.MetricsEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class MetricsPublisher {

    private final KafkaTemplate<String, MetricsEvent> kafkaTemplate;
    private final Random random = new Random();

    @Value("${agent.service-id}")
    private String serviceId;

    public MetricsPublisher(KafkaTemplate<String, MetricsEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish() {

        double cpu = 30 + random.nextDouble() * 70;
        double memory = 40 + random.nextDouble() * 60;

        MetricsEvent event = new MetricsEvent(serviceId, cpu, memory);

        kafkaTemplate.send("metrics-topic", serviceId, event);

        System.out.println("Published from " + serviceId +
                " | CPU: " + cpu +
                " | Memory: " + memory);
    }
}
