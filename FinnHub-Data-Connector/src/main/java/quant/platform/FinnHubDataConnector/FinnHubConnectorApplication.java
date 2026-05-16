package quant.platform.FinnHubDataConnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinnHubConnectorApplication {
    static void main(final String[] args) {
        SpringApplication.run(FinnHubConnectorApplication.class, args);
    }
}
