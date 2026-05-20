package quant.platform.FinnHubDataConnector.kafka;

import quant.platform.FinnHubDataConnector.message.response.type.TradeMessage;

public interface TradePublisher {
    void publish(final TradeMessage tradeMessage);
}