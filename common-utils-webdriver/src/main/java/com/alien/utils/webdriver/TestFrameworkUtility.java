package com.alien.utils.webdriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.alien.utils.webdriver.config.WebDriverConfig;

import cucumber.api.Scenario;

@ContextConfiguration(classes={WebDriverConfig.class})
public class TestFrameworkUtility {
    
    @Autowired
    protected CucumberWebDriver webDriver;
    
    protected Scenario scenario;

    
    public void registerScenario(final Scenario scenario) {
      	this.scenario = scenario;
    }
    
    public Scenario getScenario() {
    	    return scenario;
    }
    
}
