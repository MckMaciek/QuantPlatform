package quant.platform.FinnHubDataConnector.socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketConnectionClosed;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketSessionEstablished;
import quant.platform.FinnHubDataConnector.util.retry.ExponentialRetry;

import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
class WebSocketReconnectService {

    private final WebSocketConfiguration webSocketConfiguration;
    private final WebSocketClient webSocketClient;
    private final WebSocketHandler webSocketHandler;

    private final ExponentialRetry retry;

    public WebSocketReconnectService(final WebSocketConfiguration webSocketConfiguration,
                                     final WebSocketClient webSocketClient,
                                     final WebSocketHandler webSocketHandler,
                                     final ScheduledExecutorService executorService) {
        this.webSocketClient = webSocketClient;
        this.webSocketHandler = webSocketHandler;
        this.webSocketConfiguration = webSocketConfiguration;
        this.retry = new ExponentialRetry(executorService, this::reconnect);
    }

    @EventListener
    public void onConnectionClosed(final WebSocketConnectionClosed event) {
        final String sessionId = event.getSessionId();
        log.info("Connection with session: {}, has been closed - initializing reconnect", sessionId);
        retry.trigger();
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onSessionEstablished(final WebSocketSessionEstablished event) {
        this.retry.reset();
    }

    @SneakyThrows
    private void reconnect() {
        webSocketClient.execute(
                webSocketHandler,
                webSocketConfiguration.getUriString()).get();
    }
}
