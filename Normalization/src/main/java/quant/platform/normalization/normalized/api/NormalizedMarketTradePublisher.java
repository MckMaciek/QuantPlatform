package quant.platform.normalization.normalized.api;

import quant.platform.normalization.normalized.NormalizedTrade;

public interface NormalizedMarketTradePublisher {
    void publish(final NormalizedTrade trade);
}