package quant.platform.FinnHubDataConnector.util.time;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.NONE)
public class TimeUtil {

    public static <T> Measured<T> measure(final Runnable runnable) {
        final long initial = currentTimeNano();
        runnable.run();
        final long total = (initial - currentTimeNano()) / 1_000_000;
        return Measured.empty(total);
    }

    public static long currentTimeNano() {
        return System.nanoTime();
    }
}