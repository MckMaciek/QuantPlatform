package quant.platform.FinnHubDataConnector.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KafkaConfig {

    public static final String TOPIC_NAME = "finnhub-io-raw-";

    private final String serviceId;

    public KafkaConfig(@Value("${service.id:1}") final String serviceId) {
        this.serviceId = serviceId;
    }

    @Bean
    public NewTopic finnhubTopic() {
        final String topicName = String.format("%s-%s", TOPIC_NAME, serviceId);
        log.debug("Deploying finnhub kafka topic: {}", topicName);

        return new NewTopic(topicName, 1, (short) 1);
    }
}