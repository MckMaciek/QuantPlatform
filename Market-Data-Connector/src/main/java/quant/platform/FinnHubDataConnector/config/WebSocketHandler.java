package quant.platform.FinnHubDataConnector.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import quant.platform.FinnHubDataConnector.config.session.WebSocketSessionEstablished;

@Slf4j
@RequiredArgsConstructor
class WebSocketHandler extends TextWebSocketHandler {

    private static final int SEND_TIME_LIMIT_MS = 10000;

    private static final int KB = 1024;
    private static final int BUFFER_SIZE_LIMIT_KB = 512 * KB;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void afterConnectionEstablished(@NonNull final WebSocketSession unsafeSession) {
        log.info("Successfully established connection: {}", unsafeSession.getId());
        final WebSocketSession safeSession = new ConcurrentWebSocketSessionDecorator(
                unsafeSession, SEND_TIME_LIMIT_MS, BUFFER_SIZE_LIMIT_KB);
        eventPublisher.publishEvent(new WebSocketSessionEstablished(this, safeSession));
    }

    @Override
    protected void handleTextMessage(@NonNull final WebSocketSession unsafeSession,
                                     @NonNull final TextMessage message) {
        log.debug(message.getPayload());
    }

    @Override
    public void handleTransportError(@NonNull final WebSocketSession unsafeSession,
                                     @NonNull final Throwable exception) {
        log.error("Transport error, sessionId: {}", unsafeSession.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession unsafeSession,
                                      @NonNull final CloseStatus status) {
        log.info("Connection: {} closed", unsafeSession.getId());
    }
}
