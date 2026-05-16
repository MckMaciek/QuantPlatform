package quant.platform.FinnHubDataConnector.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionRetryScheduler {

    private final WebSocketConnectionManager connectionManager;

    @Scheduled(fixedRate = 15000, initialDelay = 15000)
    public void onCloseRetrySession() {
        if (!connectionManager.isConnected()) {
            log.info("Connection is closed. Restarting...");
            connectionManager.start();
        }
    }
}