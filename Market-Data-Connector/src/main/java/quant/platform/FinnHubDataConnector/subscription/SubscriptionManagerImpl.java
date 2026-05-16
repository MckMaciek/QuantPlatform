package quant.platform.FinnHubDataConnector.subscription;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessageSender;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketSessionEstablished;
import quant.platform.FinnHubDataConnector.util.time.TimeUtil;

import java.util.List;

@Slf4j
@Component
class SubscriptionManagerImpl implements SubscriptionManager {

    private final List<String> symbols;
    private final WebSocketMessageSender webSocketMessageSender;

    public SubscriptionManagerImpl(final WebSocketMessageSender webSocketMessageSender,
                                   @Value("${finnhub.symbols}") final List<String> symbols) {
        this.webSocketMessageSender = webSocketMessageSender;
        this.symbols = symbols;
    }

    @EventListener
    void sessionCreatedEvent(final WebSocketSessionEstablished event) {
        final String sessionId = event.getSafeSession().getId();
        log.info("Refreshing subscription session with sessionId: {}", sessionId);
        subscribeAll();
    }

    @Override
    public void subscribeAll() {
        log.info("Initializing subscriptions");
        final var measured = TimeUtil.measure(
                () -> symbols.forEach(
                        symbol -> webSocketMessageSender.send(SubscriptionMessage.of(symbol))));
        log.info("Initializing subscriptions finished. Took ~ {} [ms]", measured.timeTookMs());
    }

    @Override
    public void unSubscribeAll() {
        log.info("Initializing unsubscriptions");
        final var measured = TimeUtil.measure(
                () -> symbols.forEach(
                        symbol -> webSocketMessageSender.send(UnSubscriptionMessage.of(symbol))));
        log.info("Initializing unsubscriptions finished. Took ~ {} [ms]", measured.timeTookMs());
    }
}