package org.example.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEvent {

    private String serviceName;
    private String logLevel;
    private String message;
    private Long timestamp;

    public LogEvent(String service, String level, String message, long l) {
        this.serviceName=service;
        this.logLevel=level;
        this.message=message;
        this.timestamp=l;
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
