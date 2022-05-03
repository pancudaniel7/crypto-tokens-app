package com.crypto.tokens.component.listener;

import com.crypto.tokens.model.entity.EnrichedTokenEntity;
import com.crypto.tokens.service.InMemoryTokenService;
import com.crypto.tokens.service.UpdateInMemoryTokenService;
import com.crypto.tokens.util.Names;
import com.crypto.tokens.util.Numbers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.crypto.tokens.util.Names.IN_MEMORY_STRUCTURE;

@Component
@RequiredArgsConstructor
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final InMemoryTokenService inMemoryTokenService;
    private final UpdateInMemoryTokenService updateInMemoryTokenService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        ConfigurableListableBeanFactory factoryBean = event.getApplicationContext().getBeanFactory();
        registerInMemoryMapBean(factoryBean);

        inMemoryTokenService.loadData();
        inMemoryTokenService.enrichData();

        updateInMemoryTokenService.start();
    }

    private void registerInMemoryMapBean(ConfigurableListableBeanFactory beanFactory) {
        ConcurrentHashMap<String, EnrichedTokenEntity> inMemoryMap = new ConcurrentHashMap<String, EnrichedTokenEntity>();
        beanFactory.registerSingleton(IN_MEMORY_STRUCTURE, inMemoryMap);
    }
}
