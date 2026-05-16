package quant.platform.FinnHubDataConnector.socket;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketSessionEstablished;
import quant.platform.FinnHubDataConnector.util.time.Measured;
import quant.platform.FinnHubDataConnector.util.time.TimeUtil;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
class WebSocketSenderImpl implements WebSocketSender {

    private volatile WebSocketSession webSocketSession;

    @Order(1)
    @EventListener
    public void sessionCreatedEvent(final WebSocketSessionEstablished event) {
        this.setWebSocketSession(event.getSafeSession());
    }

    public void sendMessage(@NonNull final String message) {
        requireNonNull(message);

        final Measured<?> measured = TimeUtil.measure(() -> {
            if (isSessionOpen()) {
                log.debug("Sending raw message: {}", message);
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.warn("Cannot send message — session not open!");
            }
        });
        log.debug("Message processing, took ~ {} [ms]", measured.timeTookMs());
    }

    private boolean isSessionOpen() {
        return nonNull(webSocketSession) && webSocketSession.isOpen();
    }
}