package org.example.service;


import org.example.model.AlertEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {
    @KafkaListener(topics = "alerts-topic")
    public void handleAlert(AlertEvent alert) {

        System.out.println("==================================");
        System.out.println("🚨 ALERT RECEIVED");
        System.out.println("Service: " + alert.getServiceId());
        System.out.println("Level  : " + alert.getLevel());
        System.out.println("Message: " + alert.getMessage());
        System.out.println("Time   : " + alert.getTimestamp());
        System.out.println("==================================");
    }
}
