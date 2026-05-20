package quant.platform.FinnHubDataConnector.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import quant.platform.FinnHubDataConnector.message.response.type.TradeMessage;

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
        kafkaTemplate.send(String.format("finnhub-io-%s", serviceId), message);
    }
}