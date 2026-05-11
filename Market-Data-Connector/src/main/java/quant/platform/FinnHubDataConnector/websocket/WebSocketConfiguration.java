package quant.platform.FinnHubDataConnector.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
@EnableWebSocket
class WebSocketConfiguration {

    private final String finnHubUrl;
    private final String finnHubToken;

    public WebSocketConfiguration(@Value("${finnhub.url}") final String finnHubUrl,
                                  @Value("${finnhub.api-key}") final String finnHubToken) {
        this.finnHubUrl = finnHubUrl;
        this.finnHubToken = finnHubToken;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketStompClient webSocketStompClient(final WebSocketClient client) {
        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new JacksonJsonMessageConverter());

        final String fullUrl = finnHubUrl + "?token=" + finnHubToken;
        stompClient.connectAsync(fullUrl, new WebSocketHandler());

        return stompClient;
    }
}
