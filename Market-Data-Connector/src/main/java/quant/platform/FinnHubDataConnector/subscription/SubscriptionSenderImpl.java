package quant.platform.FinnHubDataConnector.subscription;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import quant.platform.FinnHubDataConnector.socket.WebSocketSender;
import tools.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
class SubscriptionSenderImpl implements SubscriptionSender {

    private final WebSocketSender webSocketSender;
    private final ObjectMapper mapper;

    @Override
    public void send(@NonNull final SubscriptionMessage message) {
        requireNonNull(message);
        final String stringified = mapper.writeValueAsString(message);
        webSocketSender.sendMessage(stringified);
    }
}