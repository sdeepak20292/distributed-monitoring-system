package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricsEvent {

    private String serviceId;
    private double cpuUsage;
    private double memoryUsage;
    private long timestamp;

    // getters + setters
}