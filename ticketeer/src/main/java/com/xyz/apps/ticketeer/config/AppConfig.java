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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.xyz.apps.ticketeer.config.api.ApiPropertyKey;

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
public class AppConfig extends EnvironmentConfig {

    /**
     * Model mapper.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    /**
     * Rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(environment.getProperty(ApiPropertyKey.BASE_URL.get())));
        return restTemplate;
    }

    /**
     * Web client builder.
     *
     * @return the web client. builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
            .baseUrl(environment.getProperty(ApiPropertyKey.BASE_URL.get()));
    }
}
