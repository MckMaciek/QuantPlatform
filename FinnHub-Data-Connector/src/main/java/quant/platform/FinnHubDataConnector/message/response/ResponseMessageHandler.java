package quant.platform.FinnHubDataConnector.message.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import quant.platform.FinnHubDataConnector.kafka.TradePublisher;
import quant.platform.FinnHubDataConnector.message.request.PongMessage;
import quant.platform.FinnHubDataConnector.message.response.parse.MessageParser;
import quant.platform.FinnHubDataConnector.message.response.type.PingMessage;
import quant.platform.FinnHubDataConnector.message.response.type.TradeMessage;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessageSender;
import quant.platform.FinnHubDataConnector.socket.session.WebSocketMessageReceived;

@Slf4j
@Component
@RequiredArgsConstructor
class ResponseMessageHandler {

    private final MessageParser messageParser;
    private final WebSocketMessageSender sender;

    private final TradePublisher tradePublisher;

    @Order(1)
    @EventListener
    public void messageReceived(final WebSocketMessageReceived event) {
        final String sessionId = event.getSessionId();
        log.info("Message received, sessionId: {}", sessionId);

        messageParser.parse(event.getMessage())
                .onPingMessage(this::runOnPingMessage)
                .onTradeMessage(this::runOnTradeMessage);
    }

    private void runOnPingMessage(final PingMessage message) {
        log.info("Ping message received: {}", message);
        sender.send(new PongMessage());
    }

    private void runOnTradeMessage(final TradeMessage message) {
        log.info("Trade message received: {}", message);
        tradePublisher.publish(message);
    }
}