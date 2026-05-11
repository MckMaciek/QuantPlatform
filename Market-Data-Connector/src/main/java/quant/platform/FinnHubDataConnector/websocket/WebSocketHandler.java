package quant.platform.FinnHubDataConnector.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
class WebSocketHandler extends TextWebSocketHandler {

    private static final int SEND_TIME_LIMIT_MS = 10000;

    private static final int KB = 1024;
    private static final int BUFFER_SIZE_LIMIT_KB = 512 * KB;

    private final WebSocketSender webSocketSender;

    @Override
    public void afterConnectionEstablished(@NonNull final WebSocketSession session) {
        log.info("Successfully established connection: {}", session.getId());
        final WebSocketSession secureSession = new ConcurrentWebSocketSessionDecorator(
                session, SEND_TIME_LIMIT_MS, BUFFER_SIZE_LIMIT_KB);
        webSocketSender.setWebSocketSession(secureSession);
    }

    @Override
    protected void handleTextMessage(@NonNull final WebSocketSession session,
                                     @NonNull final TextMessage message) {
        log.debug(message.getPayload());
    }

    @Override
    public void handleTransportError(@NonNull final WebSocketSession session,
                                     @NonNull final Throwable exception) {
        log.error("Transport error, sessionId: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession session,
                                      @NonNull final CloseStatus status) {
        log.info("Connection: {} closed", session.getId());
    }
}
