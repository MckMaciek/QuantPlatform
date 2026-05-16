package quant.platform.FinnHubDataConnector.socket;

import org.jspecify.annotations.NonNull;

public interface WebSocketSender {
    void sendMessage(@NonNull final String message);
}