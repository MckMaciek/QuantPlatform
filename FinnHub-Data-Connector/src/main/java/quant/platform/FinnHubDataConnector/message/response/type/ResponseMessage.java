package quant.platform.FinnHubDataConnector.message.response.type;


import java.util.function.Consumer;


@SuppressWarnings("UnusedReturnValue")
public class ResponseMessage {

    public ResponseMessage onTradeMessage(final Consumer<TradeMessage> dataMessageSup) {
        /* Left empty on purpose */
        return this;
    }

    public ResponseMessage onPingMessage(final Consumer<PingMessage> pingMessageSup) {
        /* Left empty on purpose */
        return this;
    }
}
