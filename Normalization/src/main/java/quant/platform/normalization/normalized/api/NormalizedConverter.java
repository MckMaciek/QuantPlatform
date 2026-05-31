package quant.platform.normalization.normalized.api;

import quant.platform.normalization.normalized.NormalizedTrade;

import java.util.List;

public interface NormalizedConverter<T> {
    List<NormalizedTrade> convert(final T t);
}