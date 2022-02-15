/*
* Id: AppConfig.java 13-Feb-2022 8:58:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The app config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com")
@EntityScan(basePackages = "com")
@EnableJpaRepositories(basePackages = "com")
@EnableMongoRepositories(basePackages = "com")
@EnableConfigurationProperties
public interface AppConfig {

    /**
     * Model mapper.
     *
     * @return the model mapper
     */
    @Bean
    public default ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
