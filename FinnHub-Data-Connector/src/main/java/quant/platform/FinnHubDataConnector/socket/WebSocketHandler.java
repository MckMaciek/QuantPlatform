package quant.platform.FinnHubDataConnector.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketConnectionClosed;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketMessageReceived;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketSessionEstablished;

@Slf4j
@Component
@RequiredArgsConstructor
class WebSocketHandler extends TextWebSocketHandler {

    private static final int SEND_TIME_LIMIT_MS = 10000;

    private static final int KB = 1024;
    private static final int BUFFER_SIZE_LIMIT_KB = 512 * KB;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void afterConnectionEstablished(@NonNull @Deprecated final WebSocketSession unsafeSession) {
        log.info("Successfully established connection: {}", unsafeSession.getId());
        final WebSocketSession safeSession = new ConcurrentWebSocketSessionDecorator(
                unsafeSession, SEND_TIME_LIMIT_MS, BUFFER_SIZE_LIMIT_KB);
        eventPublisher.publishEvent(new WebSocketSessionEstablished(this, safeSession));
    }

    @Override
    protected void handleTextMessage(@NonNull @Deprecated final WebSocketSession unsafeSession,
                                     @NonNull final TextMessage message) {
        final String sessionId = unsafeSession.getId();
        eventPublisher.publishEvent(new WebSocketMessageReceived(this, sessionId, message));
    }

    @Override
    public void afterConnectionClosed(@NonNull @Deprecated final WebSocketSession unsafeSession,
                                      @NonNull final CloseStatus status) {
        final String sessionId = unsafeSession.getId();
        log.info("Connection: {} closed", sessionId);
        eventPublisher.publishEvent(new WebSocketConnectionClosed(this, sessionId));
    }

    @Override
    public void handleTransportError(@NonNull @Deprecated final WebSocketSession unsafeSession,
                                     @NonNull final Throwable exception) {
        log.error("Transport error, sessionId: {}", unsafeSession.getId(), exception);
    }
}
