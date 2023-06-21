package com.asite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class UserApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class, args);
	}
}
