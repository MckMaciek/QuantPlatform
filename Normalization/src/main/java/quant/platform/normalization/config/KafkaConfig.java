package quant.platform.normalization.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KafkaConfig {

    public static final String NORMALIZED_TOPIC = "normalized.market";

    @Bean
    public NewTopic normalizedMarketTopic() {
        log.debug("Creating Kafka topic: {}", NORMALIZED_TOPIC);
        return new NewTopic(NORMALIZED_TOPIC, 1, (short) 1);
    }
}
