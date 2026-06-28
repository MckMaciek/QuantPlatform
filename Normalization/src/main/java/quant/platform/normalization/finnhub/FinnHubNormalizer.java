package quant.platform.normalization.finnhub;

import lombok.RequiredArgsConstructor;
import quant.platform.normalization.normalized.api.NormalizedTrade;
import quant.platform.normalization.normalized.converter.NormalizedConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class FinnHubNormalizer implements NormalizedConverter<FinnHubTrade> {

    private static final String SOURCE = "finnhub";

    @Override
    public List<NormalizedTrade> normalize(final FinnHubTrade trade) {
        return trade.data().stream()
                .map(this::toNormalizedTrade)
                .collect(Collectors.toList());
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