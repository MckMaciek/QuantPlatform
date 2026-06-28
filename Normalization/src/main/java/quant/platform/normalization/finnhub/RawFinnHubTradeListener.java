package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import quant.platform.normalization.normalized.api.NormalizedTrade;
import quant.platform.normalization.normalized.deduplication.TradeDeduplicator;
import quant.platform.normalization.normalized.publisher.NormalizedCommonTradePublisher;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class RawFinnHubTradeListener {

    private final RawFinnHubNormalizer rawFinnHubNormalizer = new RawFinnHubNormalizer();

    private final TradeDeduplicator tradeDeduplicator;

    private final NormalizedCommonTradePublisher commonPublisher;
    private final NormalizedFinnHubTradePublisher finHubPublisher;

    @SuppressWarnings("unused")
    @KafkaListener(topics = "${kafka.topics.raw-finnhub}", groupId = "${spring.kafka.consumer.group-id}")
    void onMessage(final RawFinnHubTrade trade) {
        log.info("Received FinnHub trade message, records: {}", trade.data().size());
        final List<NormalizedTrade> normalized = rawFinnHubNormalizer.normalize(trade)
                .stream()
                .filter(tradeDeduplicator::isDuplicate)
                .toList();

        normalized.forEach(commonPublisher::publish);
        normalized.forEach(finHubPublisher::publish);
    }
}