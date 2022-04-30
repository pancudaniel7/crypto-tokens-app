package com.crypto.tokens.util;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor
public final class Numbers {
    public static BigDecimal generateRandomBigDecimalFromRange(String minValue, String maxValue) {
        BigDecimal min = new BigDecimal(minValue);
        BigDecimal max = new BigDecimal(maxValue);
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(3, RoundingMode.HALF_UP);
    }
}
