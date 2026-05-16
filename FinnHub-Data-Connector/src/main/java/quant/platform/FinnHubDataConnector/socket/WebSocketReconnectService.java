package quant.platform.FinnHubDataConnector.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketConnectionClosed;

@Slf4j
@Component
class WebSocketReconnectService {

    private final WebSocketConfiguration webSocketConfiguration;
    private final WebSocketClient webSocketClient;
    private final WebSocketHandler webSocketHandler;

    public WebSocketReconnectService(final WebSocketConfiguration webSocketConfiguration,
                                     final WebSocketClient webSocketClient,
                                     final WebSocketHandler webSocketHandler) {
        this.webSocketClient = webSocketClient;
        this.webSocketHandler = webSocketHandler;
        this.webSocketConfiguration = webSocketConfiguration;
    }

    @EventListener
    public void onConnectionClosed(final WebSocketConnectionClosed event) {
        final String sessionId = event.getSessionId();
        log.info("Connection with session: {}, has been closed - initializing reconnect", sessionId);
        reconnect();
    }

    private void reconnect() {
        try {
            webSocketClient.execute(
                    webSocketHandler,
                    webSocketConfiguration.getUriString()).get();
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
    }
}
