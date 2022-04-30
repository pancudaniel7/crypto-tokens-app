package com.crypto.tokens.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.crypto.tokens.model.entity"})
public class DatabaseConfig {
}
