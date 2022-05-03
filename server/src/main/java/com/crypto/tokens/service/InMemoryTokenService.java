package com.crypto.tokens.service;

import com.crypto.tokens.model.dto.EnrichedTokenDTO;
import com.crypto.tokens.model.entity.EnrichedTokenEntity;
import com.crypto.tokens.model.entity.TokenEntity;
import com.crypto.tokens.model.error.FailToPersistException;
import com.crypto.tokens.model.error.FailToPersistInMemoryConflictException;
import com.crypto.tokens.util.transformer.EnrichedTokenTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.crypto.tokens.util.Names.*;
import static com.crypto.tokens.util.Numbers.generateRandomBigDecimalFromRange;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Collections.singletonMap;
import static java.util.Objects.isNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryTokenService {

    private final ConfigurableApplicationContext context;
    private final TokenService tokenService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${token.minVolume}")
    private String minVolume;
    @Value("${token.maxVolume}")
    private String maxVolume;
    @Value("${token.minPrice}")
    private String minPrice;
    @Value("${token.maxPrice}")
    private String maxPrice;

    @Value("${websockets.broker}")
    private String broker;

    @Value("${websockets.topics.token}")
    private String tokenTopic;

    public void loadData() {
        Map<String, EnrichedTokenEntity> inMemoryTokenMap = context.getBean(IN_MEMORY_STRUCTURE, Map.class);
        tokenService.getAll().forEach(tokenEntity -> inMemoryTokenMap.put(tokenEntity.getSymbol(), new EnrichedTokenEntity(tokenEntity.getSymbol(), tokenEntity.getToken(), null, null)));
        log.info("Loaded in memory data");
    }

    public void enrichData() {
        Map<String, EnrichedTokenEntity> inMemoryTokenMap = context.getBean(IN_MEMORY_STRUCTURE, Map.class);
        inMemoryTokenMap.forEach((key, value) -> {
            if (isNullOrEmpty(value.getPrice()) || isNullOrEmpty(value.getVolume())) {
                String randomVolume = generateRandomBigDecimalFromRange(minVolume, maxVolume).toString();
                String randomPrice = generateRandomBigDecimalFromRange(minPrice, maxPrice).toString();
                inMemoryTokenMap.put(key, new EnrichedTokenEntity(value.getSymbol(), value.getToken(), randomVolume, randomPrice));
            }
        });
        log.info("Enriched in memory data");
    }

    public Map<String, EnrichedTokenDTO> getInMemoryTokens() {
        Map<String, EnrichedTokenEntity> inMemoryTokenMap = context.getBean(IN_MEMORY_STRUCTURE, Map.class);

        Map<String, EnrichedTokenDTO> inMemoryTokenMapDTO = new HashMap<>();
        inMemoryTokenMap.forEach((key, enrichedTokenEntity) -> {
            EnrichedTokenDTO enrichedTokenDTO = EnrichedTokenTransformer.from(enrichedTokenEntity);
            inMemoryTokenMapDTO.put(key, enrichedTokenDTO);
        });

        log.info("Return in memory tokens");
        return inMemoryTokenMapDTO;
    }

    public EnrichedTokenDTO createInMemoryToken(EnrichedTokenDTO enrichedTokenDTO) {

        Map<String, EnrichedTokenEntity> inMemoryTokenMap = context.getBean(IN_MEMORY_STRUCTURE, Map.class);
        if (inMemoryTokenMap.containsKey(enrichedTokenDTO.getSymbol())) {
            throw new FailToPersistInMemoryConflictException("Fail to persist token in memory, the token exists", null);
        }

        EnrichedTokenEntity enrichedTokenEntity = EnrichedTokenTransformer.from(enrichedTokenDTO);
        TokenEntity tokenEntity = tokenService.create(enrichedTokenEntity);
        if (isNull(tokenEntity)) {
            throw new FailToPersistException("Fail to persist the token in database", null);
        }

        inMemoryTokenMap.put(enrichedTokenEntity.getSymbol(), enrichedTokenEntity);

        simpMessagingTemplate.convertAndSend(broker + tokenTopic, enrichedTokenEntity,
                singletonMap(MESSAGE_TOKEN_HEADER, MESSAGE_TOKEN_CREATE_VALUE));

        log.info("Created token with symbol: {}", enrichedTokenDTO.getSymbol());
        return enrichedTokenDTO;
    }
}
