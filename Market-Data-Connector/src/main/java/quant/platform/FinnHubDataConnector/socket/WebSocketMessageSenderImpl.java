package quant.platform.FinnHubDataConnector.socket;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import quant.platform.FinnHubDataConnector.connection.PongMessage;
import quant.platform.FinnHubDataConnector.subscription.SubscriptionMessage;
import tools.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
class WebSocketMessageSenderImpl implements WebSocketMessageSender {

    private final WebSocketRawMessageSender webSocketRawMessageSender;
    private final ObjectMapper mapper;

    @Override
    public void send(@NonNull final SubscriptionMessage message) {
        sendMessage(message);
    }

    @Override
    public void send(@NonNull final PongMessage message) {
        sendMessage(message);
    }

    private void sendMessage(final Object message) {
        requireNonNull(message);
        final String stringified = mapper.writeValueAsString(message);
        webSocketRawMessageSender.sendRawMessage(stringified);
    }
}
