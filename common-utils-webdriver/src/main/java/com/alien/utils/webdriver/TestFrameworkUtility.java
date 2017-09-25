package com.alien.utils.webdriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bskyb.acceptance.runtime.AcceptanceTestRuntimeState;

import cucumber.api.Scenario;

@Component
public class TestFrameworkUtility {

    @Autowired
    private AcceptanceTestRuntimeState acceptanceTestRuntimeState;

    public void registerScenario(final Scenario scenario) {
        acceptanceTestRuntimeState.setScenario(scenario);
    }

}
