package com.alien.utils.webdriver.drivers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alien.utils.webdriver.CucumberWebDriver;

import cucumber.api.Scenario;

public class FirefoxDriverForCucumber extends FirefoxDriver implements CucumberWebDriver {

    private static final int IS_500 = 500;
    private static final int IS_20 = 20;
    private static final String IMAGE_PNG = "image/png";
    private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxDriverForCucumber.class.getName());

    private WebDriverException cause;
    
    @Override
    public void takeScreenShot(Scenario scenario, String linkText) {

        int count = 0;

        while (!snap(scenario, linkText)) {

            count++;
            if (count > IS_20) {
            	
                LOGGER.info("Could not take screenshot", this.cause);

                break;
            }

            try {
                Thread.sleep(IS_500);
            } catch (InterruptedException e) {
                LOGGER.info("Waiting for page to load before attempting another screenshot.");
            }

        }

    }

    private boolean snap(Scenario scenario, String linkText) {

        boolean isScreenShotTaken = false;

        try {

            final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, IMAGE_PNG);
            isScreenShotTaken = true;

        } catch (WebDriverException cause) {

        	this.cause = cause;

        }

        return isScreenShotTaken;

    }

}
