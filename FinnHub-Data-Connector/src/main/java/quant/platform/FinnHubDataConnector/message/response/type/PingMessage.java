package quant.platform.FinnHubDataConnector.message.response.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.Consumer;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PingMessage extends ResponseMessage {

    private final String type;

    @Override
    public ResponseMessage onPingMessage(final Consumer<PingMessage> pingMessageSup) {
        pingMessageSup.accept(this);
        return this;
    }
}
