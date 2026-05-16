package quant.platform.FinnHubDataConnector.websocket;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import quant.platform.FinnHubDataConnector.util.time.Measured;
import quant.platform.FinnHubDataConnector.util.time.TimeUtil;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
class DefaultWebSocketSender implements WebSocketSender {

    private volatile WebSocketSession webSocketSession;

    public void sendMessage(final @NonNull String message) {
        requireNonNull(message);

        final Measured<?> measured = TimeUtil.measure(() -> {
            if (isSessionOpen()) {
                log.debug("Sending message using webSocket");
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.warn("Cannot send message — session not open");
            }
        });
        log.debug("Message processing, took {} [ms]", measured.timeTookMs());
    }

    private boolean isSessionOpen() {
        return nonNull(webSocketSession) && webSocketSession.isOpen();
    }
}
