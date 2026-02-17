package org.example.generator;

import lombok.RequiredArgsConstructor;
import org.example.events.LogEvent;
import org.example.service.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class LogGenerator {

    private final LogProducer producer;
    private final Random random = new Random();

    private final List<String> services =
            List.of("payment-service", "order-service");

    private final List<String> logLevels =
            List.of("INFO", "WARN", "ERROR");

    private final List<String> messages = List.of(
            "Payment processed",
            "Order created",
            "Database connection timeout",
            "Cache miss occurred",
            "Invalid request received"
    );


    @Scheduled(fixedRate = 3000)
    public void generateLogs() {

        String service = services.get(random.nextInt(services.size()));
        String level = logLevels.get(random.nextInt(logLevels.size()));
        String message = messages.get(random.nextInt(messages.size()));

        LogEvent event = new LogEvent(
                service,
                level,
                message,
                System.currentTimeMillis()
        );

        producer.sendLog(event);
    }
}
