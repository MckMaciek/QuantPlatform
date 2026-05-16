package quant.platform.FinnHubDataConnector.socket.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WebSocketConnectionClosed extends ApplicationEvent {

    private final String sessionId;

    public WebSocketConnectionClosed(final Object source,
                                     final String sessionId) {
        super(source);
        this.sessionId = sessionId;
    }
}