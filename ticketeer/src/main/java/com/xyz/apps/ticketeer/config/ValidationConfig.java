/*
* Id: ValidationConfig.java 11-Mar-2022 3:00:21 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The ValidationConfig.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
public class ValidationConfig {

    @Autowired
    private MessageSource messageResource;

    /**
     * Validating mongo event listener.
     *
     * @return the validating mongo event listener
     */
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    /**
     * Validator.
     *
     * @return the local validator factory bean
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageResource);
        return localValidatorFactoryBean;
    }

    /**
     * Method validation post processor.
     *
     * @return the method validation post processor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
