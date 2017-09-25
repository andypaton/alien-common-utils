package com.alien.utils.webdriver;

import org.openqa.selenium.WebDriver;
import cucumber.api.Scenario;

public class TestFrameworkUtility {
	
	private Scenario scenario;
	private WebDriver webDriver;

    public void registerScenario(final Scenario scenario) {
        this.scenario = scenario;
    }
    
    public Scenario getScenario() {
    	    return scenario;
    }

    public void setWebDriver(WebDriver webDriver) {
	    this.webDriver = webDriver;
    }
    
    public WebDriver getWebDriver() {
	    return webDriver;
    }
    
}
