package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.events.AlertEvent;
import org.example.events.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AlertEngine {

    private final KafkaTemplate<String, AlertEvent> kafkaTemplate;

    // Track error counts per service
    private final Map<String, List<Long>> errorTimestamps = new ConcurrentHashMap<>();

    @KafkaListener(topics = "processed-logs", groupId = "alert-engine-group")
    public void consume(LogEvent event) {

        // Rule 2: Keyword detection
        if (event.getMessage().contains("Database connection timeout")) {
            sendAlert(event, "DB_TIMEOUT",
                    "Database timeout detected");
        }

        // Rule 1: Error spike detection
        if ("ERROR".equals(event.getLogLevel())) {

            errorTimestamps.putIfAbsent(event.getServiceName(),
                    new ArrayList<>());

            List<Long> timestamps =
                    errorTimestamps.get(event.getServiceName());

            long now = System.currentTimeMillis();
            timestamps.add(now);

            // Remove timestamps older than 1 minute
            timestamps.removeIf(time -> now - time > 60000);

            if (timestamps.size() > 5) {
                sendAlert(event, "ERROR_SPIKE",
                        "More than 5 errors in last 1 minute");
                timestamps.clear();
            }
        }
    }

    private void sendAlert(LogEvent event,
                           String type,
                           String description) {

        AlertEvent alert = new AlertEvent(
                event.getServiceName(),
                type,
                description,
                System.currentTimeMillis()
        );

        kafkaTemplate.send("alerts",
                alert.getServiceName(),
                alert);

        System.out.println("ALERT GENERATED: " + alert);
    }
}
