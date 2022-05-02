package com.crypto.tokens.service;

import com.crypto.tokens.model.entity.EnrichedTokenEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

import static com.crypto.tokens.util.Names.IN_MEMORY_STRUCTURE;
import static com.crypto.tokens.util.Numbers.generateRandomBigDecimalFromRange;
import static com.crypto.tokens.util.Numbers.generateRandomIntFromRange;
import static java.lang.Thread.sleep;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateInMemoryTokenService {

    private final ConfigurableApplicationContext context;

    @Value("${token.minVolume}")
    private String minVolume;
    @Value("${token.maxVolume}")
    private String maxVolume;
    @Value("${token.minPrice}")
    private String minPrice;
    @Value("${token.maxPrice}")
    private String maxPrice;

    public void start() {
        Map<String, EnrichedTokenEntity> inMemoryTokenMap = context.getBean(IN_MEMORY_STRUCTURE, Map.class);
        Runnable updateInMemoryTokenThread = new UpdateInMemoryTokenThread(inMemoryTokenMap, minVolume, maxVolume, minPrice, maxPrice);
        updateInMemoryTokenThread.run();
    }

    @Slf4j
    @RequiredArgsConstructor
    static class UpdateInMemoryTokenThread implements Runnable {

        private final Map<String, EnrichedTokenEntity> inMemoryTokenMap;
        private final String minVolume, maxVolume, minPrice, maxPrice;

        @Override
        public void run() {
            try {
                update();
            } catch (InterruptedException e) {
                log.error("Fail to update in memory data structure: {}", new RuntimeException(e));
            }
        }

        private void update() throws InterruptedException {
            while (true) {

                // wait between 1 and 5 seconds
                int randomMilliSeconds = generateRandomIntFromRange.apply(1, 5) * 1000;
                sleep(randomMilliSeconds);

                // pick random inMemoryTokenMap key
                int inMemoryTokenMapSize = inMemoryTokenMap.keySet().size();
                int randomKeyIndex = generateRandomIntFromRange.apply(0, inMemoryTokenMapSize);
                String randomKey = (String) new ArrayList(inMemoryTokenMap.keySet()).get(randomKeyIndex);

                // generate random price and volume
                String randomVolume = generateRandomBigDecimalFromRange(minVolume, maxVolume).toString();
                String randomPrice = generateRandomBigDecimalFromRange(minPrice, maxPrice).toString();

                // update in inMemoryTokenMap
                EnrichedTokenEntity entity = inMemoryTokenMap.get(randomKey);
                entity.setVolume(randomVolume);
                entity.setPrice(randomPrice);
            }
        }
    }
}
