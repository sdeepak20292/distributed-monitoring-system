package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertEvent {
    private String serviceId;
    private String level;
    private String message;
    private long timestamp;
}
