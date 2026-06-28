package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import quant.platform.normalization.normalized.NormalizedTrade;
import quant.platform.normalization.normalized.api.NormalizedCommonTradePublisher;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class FinnHubTradeListener {

    private final NormalizedCommonTradePublisher commonPublisher;
    private final NormalizedFinnHubTradePublisher finHubPublisher;

    private final FinnHubNormalizer finnHubNormalizer = new FinnHubNormalizer();

    @SuppressWarnings("unused")
    @KafkaListener(topics = "${kafka.topics.raw-finnhub}", groupId = "${spring.kafka.consumer.group-id}")
    void onMessage(final FinnHubTrade trade) {
        log.info("Received FinnHub trade message, records: {}", trade.data().size());
        final List<NormalizedTrade> normalized = finnHubNormalizer.normalize(trade);

        normalized.forEach(commonPublisher::publish);
        normalized.forEach(finHubPublisher::publish);
    }
}