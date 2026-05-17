package quant.platform.FinnHubDataConnector.message.response.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.function.Consumer;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TradeMessage extends ResponseMessage {

    private final String type;
    private final List<TradeData> data;

    @Override
    public ResponseMessage onTradeMessage(final Consumer<TradeMessage> tradeMessageSup) {
        tradeMessageSup.accept(this);
        return this;
    }
}
