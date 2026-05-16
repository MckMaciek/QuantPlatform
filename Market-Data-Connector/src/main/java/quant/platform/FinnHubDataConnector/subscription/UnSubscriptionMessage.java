package quant.platform.FinnHubDataConnector.subscription;

import lombok.*;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessage;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UnSubscriptionMessage implements WebSocketMessage {

    private final String type = "unsubscribe";
    private final String symbol;

    public static UnSubscriptionMessage of(final String symbol) {
        return new UnSubscriptionMessage(symbol);
    }
}