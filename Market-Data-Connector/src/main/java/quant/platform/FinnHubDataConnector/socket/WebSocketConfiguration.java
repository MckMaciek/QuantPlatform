package quant.platform.FinnHubDataConnector.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
class WebSocketConfiguration {

    private final String finnHubUrl;
    private final String finnHubToken;

    private final WebSocketHandler webSocketHandler;

    public WebSocketConfiguration(@Value("${finnhub.url}") final String finnHubUrl,
                                  @Value("${finnhub.api-key}") final String finnHubToken,
                                  final WebSocketHandler webSocketHandler) {
        this.finnHubUrl = finnHubUrl;
        this.finnHubToken = finnHubToken;
        this.webSocketHandler = webSocketHandler;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketConnectionManager wsConnectionManager(final WebSocketClient client) {
        final WebSocketConnectionManager manager = new WebSocketConnectionManager(
                client,
                webSocketHandler,
                getUriString());
        manager.setAutoStartup(true);

        return manager;
    }

    public String getUriString() {
        return UriComponentsBuilder.fromUriString(finnHubUrl)
                .queryParam("token", finnHubToken)
                .build()
                .toUriString();
    }
}
