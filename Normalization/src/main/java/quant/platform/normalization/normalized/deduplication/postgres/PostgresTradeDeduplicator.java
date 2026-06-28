package quant.platform.normalization.normalized.deduplication.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quant.platform.normalization.normalized.api.NormalizedTrade;
import quant.platform.normalization.normalized.deduplication.TradeDeduplicator;

@Service
@RequiredArgsConstructor
class PostgresTradeDeduplicator implements TradeDeduplicator {

    @Override
    public boolean isDuplicate(final NormalizedTrade trade) {
        return false;
    }
}
