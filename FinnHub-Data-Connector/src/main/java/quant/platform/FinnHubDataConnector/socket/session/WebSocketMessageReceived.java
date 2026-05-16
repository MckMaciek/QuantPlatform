package quant.platform.FinnHubDataConnector.socket.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.TextMessage;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WebSocketMessageReceived extends ApplicationEvent {

    private final String sessionId;
    private final TextMessage message;

    public WebSocketMessageReceived(final Object source,
                                    final String sessionId,
                                    final TextMessage message) {
        super(source);
        this.sessionId = sessionId;
        this.message = message;
    }
}