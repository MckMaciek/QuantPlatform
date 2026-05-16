package quant.platform.FinnHubDataConnector.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketMessageReceived;

@Slf4j
@Component
@RequiredArgsConstructor
class MarketDataReceiver {

    @Order(1)
    @EventListener
    public void messageReceived(final WebSocketMessageReceived event) {
        final String payload = event.getMessage().getPayload();
        final String sessionId = event.getSessionId();
        log.info("Message received: {}, sessionId: {}", payload, sessionId);
    }
}