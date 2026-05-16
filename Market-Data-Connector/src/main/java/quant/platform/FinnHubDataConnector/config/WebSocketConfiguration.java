package quant.platform.FinnHubDataConnector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
class WebSocketConfiguration {

    private final String finnHubUrl;
    private final String finnHubToken;

    private final ApplicationEventPublisher eventPublisher;

    public WebSocketConfiguration(@Value("${finnhub.url}") final String finnHubUrl,
                                  @Value("${finnhub.api-key}") final String finnHubToken,
                                  final ApplicationEventPublisher eventPublisher) {
        this.finnHubUrl = finnHubUrl;
        this.finnHubToken = finnHubToken;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public WebSocketConnectionManager wsConnectionManager() {
        final WebSocketClient client = new StandardWebSocketClient();

        final UriComponents uriBuilder = UriComponentsBuilder.fromUriString(finnHubUrl)
                .queryParam("token", finnHubToken)
                .build();

        final WebSocketConnectionManager manager = new WebSocketConnectionManager(
                client,
                new WebSocketHandler(eventPublisher),
                uriBuilder.toUriString());
        manager.setAutoStartup(true);

        return manager;
    }
}
