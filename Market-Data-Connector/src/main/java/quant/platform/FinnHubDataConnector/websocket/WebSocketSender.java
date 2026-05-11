package quant.platform.FinnHubDataConnector.websocket;

import org.jspecify.annotations.NonNull;

public interface WebSocketSender {
    void sendMessage(@NonNull final String message);
}
