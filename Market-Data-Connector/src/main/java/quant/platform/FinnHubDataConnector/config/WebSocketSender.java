package quant.platform.FinnHubDataConnector.config;

import org.jspecify.annotations.NonNull;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketSender {
    void sendMessage(@NonNull final String message);

    void setWebSocketSession(@NonNull final WebSocketSession session);
}