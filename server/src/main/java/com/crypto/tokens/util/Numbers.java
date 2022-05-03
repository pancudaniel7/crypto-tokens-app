package com.crypto.tokens.util;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

@NoArgsConstructor
public final class Numbers {
    public static BigDecimal generateRandomBigDecimalFromRange(String minValue, String maxValue) {
        BigDecimal min = new BigDecimal(minValue);
        BigDecimal max = new BigDecimal(maxValue);
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(3, RoundingMode.HALF_UP);
    }

    public static BiFunction<Integer, Integer, Integer> generateRandomIntFromRange = (min, max) -> (int) ((max - min) * Math.random() + min);
}
