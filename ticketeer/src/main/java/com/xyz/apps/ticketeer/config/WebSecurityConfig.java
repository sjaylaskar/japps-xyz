/*
 * Id: WebSecurityConfig.java 14-Feb-2022 8:37:11 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * The web security config.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** The security enabled. */
    @Value("${security.enabled:false}")
    private boolean securityEnabled;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        final PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
            .inMemoryAuthentication()
            .withUser("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(encoder.encode("admin"))
            .roles("USER", "ADMIN");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {

        if (securityEnabled) {
            web.ignoring().antMatchers("/country/all", "/city/all", "/event/all", "/offer/all");
        } else {
            web.ignoring().antMatchers("/**");
        }
    }
}
