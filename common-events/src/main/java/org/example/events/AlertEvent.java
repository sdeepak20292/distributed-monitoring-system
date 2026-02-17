package org.example.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertEvent {

    private String serviceName;
    private String alertType;
    private String description;
    private Long timestamp;

}
