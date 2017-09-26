package com.alien.utils.webdriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.alien.utils.webdriver.config.WebDriverConfig;
import com.alien.utils.webdriver.runtime.RuntimeState;

import cucumber.api.Scenario;

@ContextConfiguration(classes=WebDriverConfig.class)
public class TestFrameworkUtility {
	
    @Autowired
    protected RuntimeState runtimeState;
    

    public void registerScenario(final Scenario scenario) {
      	runtimeState.setScenario(scenario);
    }
    
}
