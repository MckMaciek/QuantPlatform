package quant.platform.normalization.normalized.api;

import quant.platform.normalization.normalized.NormalizedTrade;

public interface NormalizedTradePublisher {
    void publish(final NormalizedTrade trade);
}