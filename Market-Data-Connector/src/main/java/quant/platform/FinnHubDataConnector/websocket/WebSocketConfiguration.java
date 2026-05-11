package quant.platform.FinnHubDataConnector.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
class WebSocketConfiguration {

    private final String finnHubUrl;
    private final String finnHubToken;

    private final DefaultWebSocketSender defaultWebSocketSender;

    public WebSocketConfiguration(@Value("${finnhub.url}") final String finnHubUrl,
                                  @Value("${finnhub.api-key}") final String finnHubToken,
                                  final DefaultWebSocketSender defaultWebSocketSender) {
        this.finnHubUrl = finnHubUrl;
        this.finnHubToken = finnHubToken;
        this.defaultWebSocketSender = defaultWebSocketSender;
    }

    @Bean
    public WebSocketConnectionManager wsConnectionManager() {
        final WebSocketClient client = new StandardWebSocketClient();

        final WebSocketConnectionManager manager = new WebSocketConnectionManager(
                client,
                new WebSocketHandler(defaultWebSocketSender),
                finnHubUrl + "?token=" + finnHubToken);
        manager.setAutoStartup(true);

        return manager;
    }
}
