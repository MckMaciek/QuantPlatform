package quant.platform.FinnHubDataConnector.config;

import org.jspecify.annotations.NonNull;

public interface WebSocketSender {
    void sendMessage(@NonNull final String message);
}