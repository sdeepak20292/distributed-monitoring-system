package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class MetricsEvent {

    private String serviceId;
    private double cpuUsage;
    private double memoryUsage;
    private long timestamp;


    public MetricsEvent(String serviceId, double cpuUsage, double memoryUsage) {
        this.serviceId = serviceId;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.timestamp = Instant.now().toEpochMilli();
    }

    // Getters and Setters
}