/*
* Id: MessageResourcesConfig.java 11-Mar-2022 3:56:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * The message resources config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
public class MessageResourcesConfig {

    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource
          = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
