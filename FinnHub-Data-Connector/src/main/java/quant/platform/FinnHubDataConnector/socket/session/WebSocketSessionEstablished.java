package quant.platform.FinnHubDataConnector.socket.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WebSocketSessionEstablished extends ApplicationEvent {
    private final WebSocketSession safeSession;

    public WebSocketSessionEstablished(final Object source,
                                       final WebSocketSession safeSession) {
        super(source);
        this.safeSession = safeSession;
    }
}