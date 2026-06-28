package quant.platform.normalization.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KafkaConfig {

    public static final String NORMALIZED_TOPIC = "normalized.market";

    public static final String NORMALIZED_FINN_HUB = "normalized.finnhub";

    @Bean
    public NewTopic normalizedMarketTopic() {
        log.debug("Creating Common normalized Kafka topic: {}", NORMALIZED_TOPIC);
        return new NewTopic(NORMALIZED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic normalizedFinnHubTopic() {
        log.debug("Creating FinnHub normalized Kafka topic: {}", NORMALIZED_FINN_HUB);
        return new NewTopic(NORMALIZED_FINN_HUB, 1, (short) 1);
    }
}