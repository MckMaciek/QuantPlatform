package quant.platform.normalization.finnhub;

import java.util.List;

record FinnHubTrade(String type,
                    List<FinnHubTradeData> data) {
}