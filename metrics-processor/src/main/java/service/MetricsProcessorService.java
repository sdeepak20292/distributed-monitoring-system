package service;


import org.example.models.MetricsEvent;
import org.example.models.ProcessedMetricsEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class MetricsProcessorService {

    private final KafkaTemplate<String, ProcessedMetricsEvent> kafkaTemplate;

    private final Map<String, Deque<Double>> cpuWindow = new ConcurrentHashMap<>();

    public MetricsProcessorService(KafkaTemplate<String, ProcessedMetricsEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "metrics-topic")
    public void process(MetricsEvent event) {

        cpuWindow.putIfAbsent(event.getServiceId(), new LinkedList<>());
        Deque<Double> window = cpuWindow.get(event.getServiceId());

        window.addLast(event.getCpuUsage());

        if (window.size() > 5) {
            window.removeFirst();
        }

        double avg = window.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        boolean spike = event.getCpuUsage() > 85;

        ProcessedMetricsEvent processed = new ProcessedMetricsEvent();
        processed.setServiceId(event.getServiceId());
        processed.setCpuUsage(event.getCpuUsage());
        processed.setMemoryUsage(event.getMemoryUsage());
        processed.setAvgCpuLast5(avg);
        processed.setSpikeDetected(spike);
        processed.setTimestamp(System.currentTimeMillis());

        kafkaTemplate.send("processed-metrics-topic",
                event.getServiceId(),
                processed);

        System.out.println("Processed " + event.getServiceId()
                + " | AvgCPU: " + avg
                + " | Spike: " + spike);
    }
}
