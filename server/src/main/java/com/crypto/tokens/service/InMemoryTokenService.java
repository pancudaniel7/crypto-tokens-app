package com.crypto.tokens.service;

import com.crypto.tokens.model.entity.EnrichedTokenEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.crypto.tokens.util.Numbers.generateRandomBigDecimalFromRange;
import static com.google.common.base.Strings.isNullOrEmpty;


@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryTokenService {

    private final Map<String, EnrichedTokenEntity> inMemoryTokenMap;
    private final TokenService tokenService;

    @Value("${token.minVolume}")
    private String minVolume;
    @Value("${token.maxVolume}")
    private String maxVolume;
    @Value("${token.minPrice}")
    private String minPrice;
    @Value("${token.maxPrice}")
    private String maxPrice;

    public void loadData() {
        tokenService.getAll().forEach(tokenEntity -> inMemoryTokenMap.put(tokenEntity.getSymbol(), new EnrichedTokenEntity(tokenEntity.getSymbol(), tokenEntity.getToken(), null, null)));
    }

    public void enrichData() {
        inMemoryTokenMap.forEach((key, value) -> {
            if (isNullOrEmpty(value.getPrice()) || isNullOrEmpty(value.getVolume())) {
                String randomVolume = generateRandomBigDecimalFromRange(minVolume, maxVolume).toString();
                String randomPrice = generateRandomBigDecimalFromRange(minPrice, maxPrice).toString();
                inMemoryTokenMap.put(key, new EnrichedTokenEntity(value.getSymbol(), value.getToken(), randomVolume, randomPrice));
            }
        });
    }
}
