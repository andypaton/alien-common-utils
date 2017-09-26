package com.alien.utils.webdriver.runtime;

import com.alien.utils.webdriver.AcceptanceTestException;
import com.alien.utils.webdriver.CucumberWebDriver;

import cucumber.api.Scenario;

public class RuntimeState {
	
    private static CucumberWebDriver webDriver;
    private static Scenario scenario;

    public CucumberWebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Scenario getScenario() {

        if (scenario == null) {
            throw new AcceptanceTestException(
                    "Could not retrieve Cucumber Scenario. Please ensure that a Cucumber Scenario is injected at the start of each test run.");
        }

        return scenario;
    }

    public void     setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
