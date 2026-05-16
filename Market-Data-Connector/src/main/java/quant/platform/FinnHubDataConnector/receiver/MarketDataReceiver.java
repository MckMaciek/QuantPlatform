package quant.platform.FinnHubDataConnector.receiver;

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
    public void sessionCreatedEvent(final WebSocketMessageReceived event) {
        log.debug(String.valueOf(event));
    }
}