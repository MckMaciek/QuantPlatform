package quant.platform.FinnHubDataConnector.subscription;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import quant.platform.FinnHubDataConnector.message.request.SubscriptionMessage;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessageSender;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketSessionEstablished;
import quant.platform.FinnHubDataConnector.util.time.TimeUtil;

import java.util.List;

@Slf4j
@Component
class SubscriptionManager {

    private final List<String> symbols;
    private final WebSocketMessageSender webSocketMessageSender;

    public SubscriptionManager(final WebSocketMessageSender webSocketMessageSender,
                               @Value("${finnhub.symbols}") final List<String> symbols) {
        this.webSocketMessageSender = webSocketMessageSender;
        this.symbols = symbols;
    }

    @EventListener
    void sessionCreatedEvent(final WebSocketSessionEstablished event) {
        final String sessionId = event.getSafeSession().getId();
        log.info("Refreshing subscription session with sessionId: {}", sessionId);
        subscribeAll(sessionId);
    }

    private void subscribeAll(final String sessionId) {
        log.info("Initializing subscriptions, sessionId: {}", sessionId);
        final var measured = TimeUtil.measure(
                () -> symbols.forEach(
                        symbol -> webSocketMessageSender.send(SubscriptionMessage.of(symbol))));
        log.info("Initializing subscriptions finished, sessionId: {}. Took ~ {} [ms]",
                sessionId, measured.timeTookMs());
    }
}