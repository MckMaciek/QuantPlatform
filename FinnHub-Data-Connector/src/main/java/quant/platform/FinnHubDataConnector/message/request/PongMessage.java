package quant.platform.FinnHubDataConnector.message.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import quant.platform.FinnHubDataConnector.socket.WebSocketMessage;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PongMessage implements WebSocketMessage {
    private final String type = "pong";
}