package quant.platform.normalization.normalized.converter;

import quant.platform.normalization.normalized.api.NormalizedTrade;

import java.util.List;

public interface NormalizedConverter<T> {
    List<NormalizedTrade> normalize(final T t);
}