package quant.platform.FinnHubDataConnector.util.retry;

public class RetryAbortedException extends RuntimeException {
    public RetryAbortedException(final String message) {
        super(message);
    }
}