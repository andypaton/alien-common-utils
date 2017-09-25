package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alien.utils.webdriver.AcceptanceTestException;
import com.alien.utils.webdriver.TestFrameworkUtility;

public class WebDriverUtility extends TestFrameworkUtility {

    private DriverFactory driverFactory;

    private ApplicationContext context;

    public CucumberWebDriver getWebDriver() {
        return acceptanceTestRuntimeState.getWebDriver();
    }

    public void takeScreenShot(String description) {
        getWebDriver().takeScreenShot(acceptanceTestRuntimeState.getScenario(), description);
    }

    public void takeScreenShot() {
        takeScreenShot("Screenshot");
    }
    
    public void executeJavaScript(String script) {
    	((JavascriptExecutor) getWebDriver()).executeScript(script);
    }

    public void closeDriver() {

        if (acceptanceTestRuntimeState.getWebDriver() != null) {
        	acceptanceTestRuntimeState.getWebDriver().quit();
        	acceptanceTestRuntimeState.setWebDriver(null);
        }

    }

    public void TestFrameworkUtility(String targetEndpoint) {
        registerTargetEndpoint(targetEndpoint, true);
    }

    public void registerTargetEndpoint(String targetEndpoint, boolean useExistingDriver) {
        context.getApplicationName();

        if (!useExistingDriver) {
            closeDriver();
        }

        try {
            if (acceptanceTestRuntimeState.getWebDriver() != null) {
            	acceptanceTestRuntimeState.getWebDriver().get(targetEndpoint);
            } else {
                final CucumberWebDriver webDriver = driverFactory.getInstance();
                acceptanceTestRuntimeState.setWebDriver(webDriver);
                acceptanceTestRuntimeState.getWebDriver().get(targetEndpoint);
            }
        } catch (IOException | URISyntaxException e) {
            throw new AcceptanceTestException(
                    "An error occurred registering a target endpoint via a Selenium web driver", e);
        }
    }
}
