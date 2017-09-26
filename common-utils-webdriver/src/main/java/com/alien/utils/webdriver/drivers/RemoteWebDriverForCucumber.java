package com.alien.utils.webdriver.drivers;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.alien.utils.webdriver.CucumberWebDriver;

import cucumber.api.Scenario;


public class RemoteWebDriverForCucumber extends RemoteWebDriver implements CucumberWebDriver {

    private static final String IMAGE_PNG = "image/png";

    public RemoteWebDriverForCucumber(URL url, Capabilities capabilities) {

        super(url, capabilities);

    }

    @Override
    public void takeScreenShot(Scenario scenario, String linkText) {

        final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, IMAGE_PNG);

    }

}
