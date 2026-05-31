package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import quant.platform.normalization.normalized.NormalizedTrade;
import quant.platform.normalization.normalized.NormalizedTradePublisher;

import java.math.BigDecimal;

@Slf4j
@Component
@SuppressWarnings("unused")
@RequiredArgsConstructor
class FinnHubTradeListener {

    private static final String SOURCE = "finnhub";

    private final NormalizedTradePublisher publisher;

    @KafkaListener(topics = "${kafka.topics.raw-finnhub}", groupId = "${spring.kafka.consumer.group-id}")
    void onMessage(final FinnHubTrade trade) {
        log.info("Received FinnHub trade message, records: {}", trade.data().size());
        trade.data().stream()
                .map(this::toNormalizedTrade)
                .forEach(publisher::publish);
    }

    private NormalizedTrade toNormalizedTrade(final FinnHubTradeData data) {
        final long timestampMs = Long.parseLong(data.t());
        return new NormalizedTrade(
                data.s(),
                new BigDecimal(data.p()),
                new BigDecimal(data.v()),
                timestampMs,
                SOURCE,
                data.s() + "_" + timestampMs
        );
    }
}
