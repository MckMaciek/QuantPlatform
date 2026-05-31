package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import quant.platform.normalization.normalized.api.NormalizedTradePublisher;

@Slf4j
@Component
@RequiredArgsConstructor
class FinnHubTradeListener {

    private final NormalizedTradePublisher publisher;
    private final FinnHubConverter finnHubConverter = new FinnHubConverter();

    @SuppressWarnings("unused")
    @KafkaListener(topics = "${kafka.topics.raw-finnhub}", groupId = "${spring.kafka.consumer.group-id}")
    void onMessage(final FinnHubTrade trade) {
        log.info("Received FinnHub trade message, records: {}", trade.data().size());
        finnHubConverter.convert(trade)
                .forEach(publisher::publish);
    }
}