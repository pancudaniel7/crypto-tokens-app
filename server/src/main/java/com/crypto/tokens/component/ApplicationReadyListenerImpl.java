package com.crypto.tokens.component;

import com.crypto.tokens.model.entity.EnrichedTokenEntity;
import com.crypto.tokens.service.InMemoryTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ApplicationReadyListenerImpl implements ApplicationListener<ApplicationReadyEvent> {

    private final InMemoryTokenService inMemoryTokenService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        ConfigurableListableBeanFactory factoryBean = event.getApplicationContext().getBeanFactory();
        registerInMemoryMapBean(factoryBean);

        inMemoryTokenService.loadData();
        inMemoryTokenService.enrichData();
    }

    private void registerInMemoryMapBean(ConfigurableListableBeanFactory beanFactory) {
        ConcurrentHashMap<String, EnrichedTokenEntity> inMemoryMap = new ConcurrentHashMap<String, EnrichedTokenEntity>();
        beanFactory.registerSingleton("inMemoryTokenMap", inMemoryMap);
    }
}
