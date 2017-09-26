package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.JavascriptExecutor;

import com.alien.utils.webdriver.AcceptanceTestException;
import com.alien.utils.webdriver.TestFrameworkUtility;
import com.alien.utils.webdriver.runtime.RuntimeState;

public class WebDriverUtility extends TestFrameworkUtility {

    private DriverFactory driverFactory;
//    private RuntimeState runtimeState;

	public CucumberWebDriver getWebDriver() {
        return runtimeState.getWebDriver();
    }

    public void takeScreenShot(String description) {
        getWebDriver().takeScreenShot(runtimeState.getScenario(), description);
    }

    public void takeScreenShot() {
        takeScreenShot("Screenshot");
    }
    
    public void executeJavaScript(String script) {
    	((JavascriptExecutor) getWebDriver()).executeScript(script);
    }

    public void closeDriver() {

        if (runtimeState.getWebDriver() != null) {
        	    runtimeState.getWebDriver().quit();
        	    runtimeState.setWebDriver(null);
        }

    }

    public void TestFrameworkUtility(String targetEndpoint) {
        registerTargetEndpoint(targetEndpoint, true);
    }

    public void registerTargetEndpoint(String targetEndpoint, boolean useExistingDriver) {
    	
//        context.getApplicationName();

        if (!useExistingDriver) {
            closeDriver();
        }

        try {
            if (runtimeState.getWebDriver() != null) {
            	    runtimeState.getWebDriver().get(targetEndpoint);
            } else {
                final CucumberWebDriver webDriver = driverFactory.getInstance();
                runtimeState.setWebDriver(webDriver);
                runtimeState.getWebDriver().get(targetEndpoint);
            }
        } catch (IOException | URISyntaxException e) {
            throw new AcceptanceTestException("An error occurred registering a target endpoint via a Selenium web driver", e);
        }
    }
}
