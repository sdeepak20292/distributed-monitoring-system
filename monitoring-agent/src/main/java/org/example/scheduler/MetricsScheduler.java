package org.example.scheduler;

import org.example.service.MetricsPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsScheduler {

    private final MetricsPublisher publisher;

    public MetricsScheduler(MetricsPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRate = 2000)
    public void generate() {
        publisher.publish();
    }
}