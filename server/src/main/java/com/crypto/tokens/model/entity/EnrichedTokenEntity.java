package com.crypto.tokens.model.entity;

import lombok.Data;

@Data
public class EnrichedTokenEntity extends TokenEntity {
    private String volume;
    private String price;

    public EnrichedTokenEntity(String symbol, String token, String volume, String price) {
        super.setSymbol(symbol);
        super.setToken(token);
        this.volume = volume;
        this.price = price;
    }
}