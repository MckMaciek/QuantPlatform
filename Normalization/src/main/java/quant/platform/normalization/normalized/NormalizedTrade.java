package quant.platform.normalization.normalized;

import java.math.BigDecimal;

public record NormalizedTrade(
        String symbol,
        BigDecimal price,
        BigDecimal volume,
        long timestampMs,
        String source,
        String tradeId
) {
}
