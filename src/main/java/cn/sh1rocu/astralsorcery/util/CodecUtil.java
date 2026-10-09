package cn.sh1rocu.astralsorcery.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.function.Function;

public class CodecUtil {
    private CodecUtil() {
    }

    public static final Codec<Long> POSITIVE_LONG = longRangeWithMessage(1L, Long.MAX_VALUE, (l) -> "Value must be positive: " + l);

    public static Codec<Long> longRangeWithMessage(long min, long max, Function<Long, String> errorMessage) {
        return Codec.LONG.validate((l) -> l.compareTo(min) >= 0 && l.compareTo(max) <= 0 ? DataResult.success(l) :
                DataResult.error(() -> errorMessage.apply(l)));
    }
}
