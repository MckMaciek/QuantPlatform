package quant.platform.FinnHubDataConnector.util.time;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.NONE)
public class TimeUtil {

    public static <T> Measured<T> measure(final Runnable runnable) {
        final long initial = currentTimeMs();
        runnable.run();
        final long total = initial - currentTimeMs();
        return Measured.empty(total);
    }

    public static long currentTimeMs() {
        return System.currentTimeMillis() / 1000;
    }
}