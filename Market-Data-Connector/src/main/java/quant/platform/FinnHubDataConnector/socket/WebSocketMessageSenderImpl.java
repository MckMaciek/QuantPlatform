package quant.platform.FinnHubDataConnector.socket;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
class WebSocketMessageSenderImpl implements WebSocketMessageSender {

    private final WebSocketRawMessageSender webSocketRawMessageSender;
    private final ObjectMapper mapper;

    @Override
    public void send(@NonNull final WebSocketMessage message) {
        requireNonNull(message);
        final String stringified = mapper.writeValueAsString(message);
        webSocketRawMessageSender.sendRawMessage(stringified);
    }
}