package quant.platform.normalization.normalized.publisher;

import quant.platform.normalization.normalized.api.NormalizedTrade;

public interface NormalizedCommonTradePublisher {
    void publish(final NormalizedTrade trade);
}