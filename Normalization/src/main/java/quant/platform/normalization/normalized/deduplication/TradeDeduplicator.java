package quant.platform.normalization.normalized.deduplication;

import quant.platform.normalization.normalized.api.NormalizedTrade;

/**
 * This interface should be abstract, and should not leak storage used under the hood.
 */
public interface TradeDeduplicator {
    boolean isDuplicate(final NormalizedTrade trade);
}
