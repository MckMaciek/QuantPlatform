package quant.platform.FinnHubDataConnector.socket;

import org.jspecify.annotations.NonNull;

public interface WebSocketMessageSender {
    void send(@NonNull final WebSocketMessage message);
}