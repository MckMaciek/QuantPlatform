package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import quant.platform.normalization.config.KafkaConfig;
import quant.platform.normalization.normalized.NormalizedTrade;

@Slf4j
@Service
@RequiredArgsConstructor
class NormalizedFinnHubTradePublisher {

    private final KafkaTemplate<String, NormalizedTrade> kafkaTemplate;

    public void publish(final NormalizedTrade trade) {
        log.debug("Publishing FinnHub Normalized trade: symbol: {}, price: {}, source: {}",
                trade.symbol(), trade.price(), trade.source());
        kafkaTemplate.send(KafkaConfig.NORMALIZED_FINN_HUB, trade.symbol(), trade);
    }
}