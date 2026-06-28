package quant.platform.normalization.finnhub;

import java.util.List;

record RawFinnHubTrade(String type,
                       List<RawFinnHubTradeData> data) {
}