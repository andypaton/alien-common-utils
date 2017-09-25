package com.alien.utils.webdriver;

import cucumber.api.Scenario;

public class TestFrameworkUtility {

    public void registerScenario(final Scenario scenario) {
        acceptanceTestRuntimeState.setScenario(scenario);
    }

}
