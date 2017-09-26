package com.alien.utils.webdriver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alien.utils.webdriver.WebDriverUtility;
import com.alien.utils.webdriver.runtime.RuntimeState;

@Configuration
@ComponentScan(basePackages={"com.alien.utils.webdriver"})
public class WebDriverConfig {

	@Bean()
	RuntimeState runtimeState() {
		return new RuntimeState();
	}
	
	@Bean()
	WebDriverUtility webDriverUtility() {
		return new WebDriverUtility();
	}
}
