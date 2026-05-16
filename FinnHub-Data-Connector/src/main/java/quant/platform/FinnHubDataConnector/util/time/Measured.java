package quant.platform.FinnHubDataConnector.util.time;

public record Measured<T>(long timeTookMs,
                          T result) {

    public static <T> Measured<T> empty(final long timeTook) {
        return new Measured<>(timeTook, null);
    }
}