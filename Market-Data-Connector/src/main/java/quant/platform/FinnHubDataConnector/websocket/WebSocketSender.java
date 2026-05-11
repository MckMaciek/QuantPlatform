package quant.platform.FinnHubDataConnector.websocket;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static java.util.Objects.nonNull;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
public class WebSocketSender {

    private WebSocketSession webSocketSession;

    @SneakyThrows
    public void sendMessage(final String message) {
        if (isSessionOpen()) {
            log.debug("Sending message using webSocket");
            webSocketSession.sendMessage(new TextMessage(message));
        }
    }

    private boolean isSessionOpen() {
        return nonNull(webSocketSession) && webSocketSession.isOpen();
    }
}
