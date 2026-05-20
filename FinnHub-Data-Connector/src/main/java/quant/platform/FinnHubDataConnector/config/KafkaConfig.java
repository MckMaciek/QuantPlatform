package quant.platform.FinnHubDataConnector.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KafkaConfig {

    private final String serviceId;

    public KafkaConfig(@Value("${service.id:1}") final String serviceId) {
        this.serviceId = serviceId;
    }

    @Bean
    public NewTopic finnhubTopic() {
        return new NewTopic(String.format("finnhub-io-%s", serviceId), 1, (short) 1);
    }
}