package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessedMetricsEvent {

    private String serviceId;
    private double cpuUsage;
    private double memoryUsage;
    private double avgCpuLast5;
    private boolean spikeDetected;
    private long timestamp;

    // getters + setters
}