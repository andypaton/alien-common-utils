package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.JavascriptExecutor;

public class WebDriverUtility extends TestFrameworkUtility {

    private static DriverFactory driverFactory = new DriverFactory();
   

	public CucumberWebDriver getWebDriver() {
		return webDriver;
    }

    public void takeScreenShot(String description) {
        getWebDriver().takeScreenShot(scenario, description);
    }

    public void takeScreenShot() {
        takeScreenShot("Screenshot");
    }
    
    public void executeJavaScript(String script) {
    	((JavascriptExecutor) getWebDriver()).executeScript(script);
    }

    public void closeDriver() {

        if (webDriver != null) {
        	webDriver.quit();
        	webDriver = null;
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
            if (webDriver != null) {
            	
            	    webDriver.get(targetEndpoint);
            	
            } else {

            	    webDriver = driverFactory.getInstance();
            	    webDriver.get(targetEndpoint);
            }
        } catch (IOException | URISyntaxException e) {
            throw new AcceptanceTestException("An error occurred registering a target endpoint via a Selenium web driver", e);
        }
    }
}
