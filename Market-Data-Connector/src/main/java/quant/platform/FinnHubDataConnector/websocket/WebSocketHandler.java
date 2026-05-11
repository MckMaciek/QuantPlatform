package quant.platform.FinnHubDataConnector.websocket;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

@Slf4j
class WebSocketHandler implements StompSessionHandler {

    @Override
    public void afterConnected(@NonNull final StompSession session,
                               @NonNull final StompHeaders connectedHeaders) {
        log.info("Successfully established connection: {}",
                session.getSessionId());
    }

    @Override
    public void handleException(@NonNull final StompSession session,
                                @Nullable final StompCommand command,
                                @NonNull final StompHeaders headers,
                                final byte @NonNull [] payload,
                                @NonNull final Throwable exception) {
        log.error("Exception occurred, sessionId: {}",
                session.getSessionId(),
                exception);
    }

    @Override
    public void handleTransportError(@NonNull final StompSession session,
                                     @NonNull final Throwable exception) {
        log.error("Transport error, sessionId: {}",
                session.getSessionId(),
                exception);
    }

    @Override
    public @NonNull Type getPayloadType(@NonNull final StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(@NonNull final StompHeaders headers,
                            @Nullable final Object payload) {
        final String msg = (String) payload;
        log.debug(msg);
    }
}
