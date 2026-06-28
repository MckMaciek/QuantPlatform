package quant.platform.normalization.normalized.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import quant.platform.normalization.config.KafkaConfig;
import quant.platform.normalization.normalized.api.NormalizedTrade;

@Slf4j
@Service
@RequiredArgsConstructor
class NormalizedCommonTradePublisherImpl implements NormalizedCommonTradePublisher {

    private final KafkaTemplate<String, NormalizedTrade> kafkaTemplate;

    @Override
    public void publish(final NormalizedTrade trade) {
        log.debug("Publishing normalized trade: symbol: {}, price: {}, source: {}",
                trade.symbol(), trade.price(), trade.source());
        kafkaTemplate.send(KafkaConfig.NORMALIZED_TOPIC, trade.symbol(), trade);
    }
}