package quant.platform.FinnHubDataConnector.subscription;

import org.jspecify.annotations.NonNull;

public interface SubscriptionSender {
    void send(@NonNull final SubscriptionMessage message);
}