package quant.platform.FinnHubDataConnector.message.response.parse;

import org.springframework.web.socket.TextMessage;
import quant.platform.FinnHubDataConnector.message.response.type.ResponseMessage;

public interface MessageParser {
    ResponseMessage parse(final TextMessage message);
}