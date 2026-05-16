package quant.platform.FinnHubDataConnector.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import quant.platform.FinnHubDataConnector.subscription.SubscriptionManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionRefreshScheduler {

    private final WebSocketConnectionManager connectionManager;
    private final SubscriptionManager subscriptionManager;

    @Scheduled(fixedRate = 8000, initialDelay = 8000)
    public void onCloseRetrySession() {
        if (connectionManager.isConnected()) {
            subscriptionManager.unSubscribeAll();
            subscriptionManager.subscribeAll();
        }
    }
}