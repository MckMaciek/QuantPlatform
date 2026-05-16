package quant.platform.FinnHubDataConnector.socket;

import org.jspecify.annotations.NonNull;
import quant.platform.FinnHubDataConnector.connection.PongMessage;
import quant.platform.FinnHubDataConnector.subscription.SubscriptionMessage;

public interface WebSocketMessageSender {
    void send(@NonNull final SubscriptionMessage message);

    void send(@NonNull final PongMessage message);
}