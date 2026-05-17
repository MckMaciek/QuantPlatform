package quant.platform.FinnHubDataConnector.util.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ExponentialRetry {

    private static final int MAX_ATTEMPTS = 15;
    private static final int MAX_DELAY_SECONDS = 300;

    private volatile int attemptCount = 0;

    private final ScheduledExecutorService executorService;
    private final Runnable retryAction;

    public synchronized void trigger() {
        ++attemptCount;
        int delaySeconds = backoffDelaySeconds(attemptCount);
        log.debug("Scheduling retry attempt {}/{} in {}s", attemptCount, MAX_ATTEMPTS, delaySeconds);
        executorService.schedule(this::executeAttempt, delaySeconds, TimeUnit.SECONDS);
    }

    public synchronized void reset() {
        this.attemptCount = 0;
    }

    private void executeAttempt() {
        try {
            log.debug("Executing retry attempt {}/{}", attemptCount, MAX_ATTEMPTS);
            retryAction.run();
        } catch (final Exception exception) {
            log.error("Attempt {}/{} failed", attemptCount, MAX_ATTEMPTS, exception);
            if (attemptCount < MAX_ATTEMPTS) {
                trigger();
            } else {
                log.error("Aborted retry after {} attempts", attemptCount);
                throw new RetryAbortedException(
                        String.format("Aborted after %d attempts", MAX_ATTEMPTS));
            }
        }
    }

    private int backoffDelaySeconds(final int attempt) {
        return Math.min(1 << attempt, MAX_DELAY_SECONDS);
    }
}