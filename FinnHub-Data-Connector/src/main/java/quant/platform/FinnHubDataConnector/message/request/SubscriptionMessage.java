package quant.platform.FinnHubDataConnector.message.request;

import lombok.*;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessage;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionMessage implements WebSocketMessage {

    private final String type = "subscribe";
    private final String symbol;

    public static SubscriptionMessage of(final String symbol) {
        return new SubscriptionMessage(symbol);
    }
}