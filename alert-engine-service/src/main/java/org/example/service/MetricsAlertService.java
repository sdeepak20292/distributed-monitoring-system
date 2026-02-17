package org.example.service;


import org.example.model.AlertEvent;
import org.example.model.ProcessedMetricsEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetricsAlertService {

    private final KafkaTemplate<String, AlertEvent> kafkaTemplate;

    public MetricsAlertService(KafkaTemplate<String, AlertEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @KafkaListener(topics = "processed-metrics-topic")
    public void evaluate(ProcessedMetricsEvent event) {

        String level = null;
        String message = null;

        if (event.getCpuUsage() > 90) {
            level = "CRITICAL";
            message = "CPU above 90%";
        }
        else if (event.isSpikeDetected()) {
            level = "HIGH";
            message = "Spike detected";
        }
        else if (event.getAvgCpuLast5() > 80) {
            level = "WARNING";
            message = "Average CPU high";
        }
        if (level != null) {
            AlertEvent alert = new AlertEvent();
            alert.setServiceId(event.getServiceId());
            alert.setLevel(level);
            alert.setMessage(message);
            alert.setTimestamp(System.currentTimeMillis());

            kafkaTemplate.send("alerts-topic",
                    event.getServiceId(),
                    alert);

            System.out.println("ALERT → "
                    + level + " | "
                    + event.getServiceId() + " | "
                    + message);
        }
    }
}

