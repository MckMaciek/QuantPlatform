package quant.platform.FinnHubDataConnector.websocket;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
class WebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(@NonNull final WebSocketSession session,
                                     @NonNull final TextMessage message) {
        log.debug(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(@NonNull final WebSocketSession session) {
        log.info("Successfully established connection: {}",
                session.getId());
    }

    @Override
    public void handleTransportError(@NonNull final WebSocketSession session,
                                     @NonNull final Throwable exception) {
        log.error("Transport error, sessionId: {}",
                session.getId(),
                exception);
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession session,
                                      @NonNull final CloseStatus status) {
        log.info("Connection: {} closed",
                session.getId());
    }
}
