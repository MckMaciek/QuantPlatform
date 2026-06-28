package quant.platform.normalization.normalized.deduplication;

import quant.platform.normalization.normalized.api.NormalizedTrade;

/**
 * This interface should be abstract, and should not leak storage used under the hood.
 */
public interface TradeDeduplicator {

    /**
     * The same trade can be potentially already processed i.e. by different service instance etc.
     * Thus need to check, whether current trade is a duplicate or not.
     * Using this service guarantees exactly once delivery semantics.
     *
     * @param trade normalized trade.
     * @return true in case the trade has been already processed, false otherwise.
     */
    boolean isDuplicate(final NormalizedTrade trade);
}