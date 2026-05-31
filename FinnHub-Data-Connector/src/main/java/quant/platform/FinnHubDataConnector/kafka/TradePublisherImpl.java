package quant.platform.FinnHubDataConnector.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import quant.platform.FinnHubDataConnector.config.KafkaConfig;
import quant.platform.FinnHubDataConnector.message.response.type.TradeMessage;

import static java.util.Objects.nonNull;

@Slf4j
@Service
class TradePublisherImpl implements TradePublisher {

    private final KafkaTemplate<String, TradeMessage> kafkaTemplate;
    private final String serviceId;

    public TradePublisherImpl(final KafkaTemplate<String, TradeMessage> kafkaTemplate,
                              @Value("${service.id:1}") final String serviceId) {
        this.kafkaTemplate = kafkaTemplate;
        this.serviceId = serviceId;
    }

    @Override
    public void publish(final TradeMessage message) {
        final String topicName = String.format("%s-%s", KafkaConfig.TOPIC_NAME, serviceId);
        kafkaTemplate.send(topicName, message)
                .whenComplete((result, exception) -> {
                    if (nonNull(exception)) {
                        log.error("Failed to publish trade message to kafka topic: {}",
                                topicName, exception);
                    }
                });
    }
}