package com.alien.utils.webdriver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alien.utils.webdriver.CucumberWebDriver;
import com.alien.utils.webdriver.WebDriverUtility;

@Configuration
@ComponentScan(basePackages={"com.alien.utils.webdriver"})
public class WebDriverConfig {
	
	@Bean(destroyMethod="quit")
	CucumberWebDriver webDriver() {
		CucumberWebDriver webdriver =  null;
		return webdriver;
	}
	
//	@Bean()
//	WebDriverUtility webDriverUtility() {
//		return new WebDriverUtility();
//	}
}
