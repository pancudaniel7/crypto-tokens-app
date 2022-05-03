package com.crypto.tokens.util.transformer;

import com.crypto.tokens.model.dto.EnrichedTokenDTO;
import com.crypto.tokens.model.entity.EnrichedTokenEntity;

public final class EnrichedTokenTransformer {
    public static EnrichedTokenDTO from(EnrichedTokenEntity entity){
        return new EnrichedTokenDTO(entity.getSymbol(), entity.getToken(), entity.getVolume(), entity.getPrice());
    }

    public static EnrichedTokenEntity from(EnrichedTokenDTO dto){
        return new EnrichedTokenEntity(dto.getSymbol(), dto.getToken(), dto.getVolume(), dto.getPrice());
    }
}
