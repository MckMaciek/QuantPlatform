package quant.platform.FinnHubDataConnector.message.response.parse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import quant.platform.FinnHubDataConnector.message.response.type.PingMessage;
import quant.platform.FinnHubDataConnector.message.response.type.ResponseMessage;
import quant.platform.FinnHubDataConnector.message.response.type.TradeMessage;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class MessageParserImpl implements MessageParser {

    private static final Map<String, Class<? extends ResponseMessage>> TYPE_REGISTRY = Map.of(
            "ping", PingMessage.class,
            "trade", TradeMessage.class
    );

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public ResponseMessage parse(final TextMessage message) {
        final JsonNode node = objectMapper.readTree(message.getPayload());
        final String type = node.path("type").asString();
        final Class<? extends ResponseMessage> targetClass = TYPE_REGISTRY.get(type);
        if (targetClass == null) {
            log.warn("Received unknown message type: '{}', payload: {}", type, message.getPayload());
            return new ResponseMessage();
        }
        return objectMapper.treeToValue(node, targetClass);
    }
}
