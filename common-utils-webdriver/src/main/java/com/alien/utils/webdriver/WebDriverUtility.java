package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.JavascriptExecutor;

import com.alien.utils.webdriver.AcceptanceTestException;
import com.alien.utils.webdriver.TestFrameworkUtility;

public class WebDriverUtility extends TestFrameworkUtility {

    private DriverFactory driverFactory;

    public CucumberWebDriver getWebDriver() {
        return getWebDriver();
    }

    public void takeScreenShot(String description) {
        getWebDriver().takeScreenShot(getScenario(), description);
    }

    public void takeScreenShot() {
        takeScreenShot("Screenshot");
    }
    
    public void executeJavaScript(String script) {
    	((JavascriptExecutor) getWebDriver()).executeScript(script);
    }

    public void closeDriver() {

        if (getWebDriver() != null) {
        	    getWebDriver().quit();
        	    setWebDriver(null);
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
            if (getWebDriver() != null) {
            	    getWebDriver().get(targetEndpoint);
            } else {
                final CucumberWebDriver webDriver = driverFactory.getInstance();
                setWebDriver(webDriver);
                getWebDriver().get(targetEndpoint);
            }
        } catch (IOException | URISyntaxException e) {
            throw new AcceptanceTestException("An error occurred registering a target endpoint via a Selenium web driver", e);
        }
    }
}
