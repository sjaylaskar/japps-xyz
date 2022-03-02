/*
* Id: TicketeerApplication.java 14-Feb-2022 12:14:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.xyz.apps.ticketeer.config.AppConfig;

/**
 * The ticketeer application.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TicketeerApplication implements AppConfig {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(TicketeerApplication.class, args);
	}
}
